import java.io.*;
import java.util.*;

public class ASTBuilder extends LittleBaseListener {
	Hashtable<String, String> varsType; //stores declared vars and their types
	Hashtable<Integer, String> varsOrder; //stores ordered declaration of variables
	Hashtable<String, String> strValue; //stores value of string variable
	Stack<AST> trees; //stores all ASTs (Queue = first in first out)
	Stack<CodeObject> IRCode;
	Stack<String> tinyCode;
	int tempIRNum = 0; //Number of temps generated for IR code representation
	int varCount = 0; //Keeps order of variable declarations

	/*no arg constructor*/
	public ASTBuilder(){
		varsType = new Hashtable<>();
		varsOrder = new Hashtable<>();
		strValue = new Hashtable<>();
		trees = new Stack<>();
		IRCode = new Stack<CodeObject>();
		tinyCode = new Stack<>();
	}

	/*Store variables' that are FLOAT or INT*/
	@Override
	public void enterVar_decl(LittleParser.Var_declContext ctx) {
		String[] allVars = (ctx.id_list().getText()).split(",");

		for(int i = 0; i < allVars.length; i++){
			varsType.put(allVars[i], ctx.var_type().getText());
			varsOrder.put(varCount, allVars[i]);
			varCount++;
		}
	}

	/*Make AST nodes for simple assignment expressions*/
	@Override
	public void enterAssign_expr(LittleParser.Assign_exprContext ctx) {
		AST root = new AST(); //create new AST
		root.value = ":=";
		//System.out.println("TEST"+ ctx.id().getText() + " " + ctx.expr().getText());
		AST leftNode = new AST();
		leftNode.value = "VARREF " + ctx.id().getText() + " " + varsType.get(ctx.id().getText());
		root.left = leftNode;
		trees.push(root);
	}

	/*Enter addition or subtraction operation*/
	@Override
	public void enterAddop(LittleParser.AddopContext ctx) {
		AST root = trees.pop();
		AST rightNode = new AST();
		if(root.right != null){
			AST temp = root.right;
			rightNode.value = "ADDOP " + ctx.getText();
			rightNode.left = temp; //ex: e+f 'e' will be the leftchild of '+'
			root.right = rightNode;
			trees.push(root);
			return;
		}
		rightNode.value = "ADDOP " + ctx.getText();
		root.right = rightNode;
		trees.push(root);
	}

	/*Enter multiplication or division operation*/
	@Override
	public void enterMulop(LittleParser.MulopContext ctx) {
		AST root = trees.pop();
		AST rightNode = new AST();
		if(root.right != null){
			AST temp = root.right;
			rightNode.value = "MULOP " + ctx.getText();
			rightNode.left = temp; //ex: e+f 'e' will be the leftchild of '+'
			root.right = rightNode;
			trees.push(root);
			return;
		}
		rightNode.value = "MULOP " + ctx.getText();
		root.right = rightNode;
		trees.push(root);
	}

	/*Enter String declaration*/
	@Override
	public void enterString_decl(LittleParser.String_declContext ctx) {
		AST root = new AST();
		root.value = "STRING " + ctx.id().getText() + " " + ctx.str().getText();
		varsType.put(ctx.id().getText(), "STRING");
		varsOrder.put(varCount, ctx.id().getText());
		strValue.put(ctx.id().getText(), ctx.str().getText());
		varCount++;
		trees.push(root);
	}

	@Override
	public void enterPrimary(LittleParser.PrimaryContext ctx) {
		AST root = trees.pop();
		AST rightNode = root.right;
		String primaryValue = ctx.getText();

		boolean numeric = true;
		boolean parenthesisExpr = primaryValue.startsWith("(");
		numeric = (primaryValue).matches("-?\\d+(\\.\\d+)?");
		if( numeric ) {
			primaryValue = "CONSTANT " + ctx.getText();
		} else {
			primaryValue = "VARREF " + ctx.getText() + " " + varsType.get(ctx.getText());
		}

		if(rightNode == null){
			rightNode = new AST();
			rightNode.value = primaryValue;

			/*Expressions with parenthesis can be ignored*/
			if( parenthesisExpr ){
				root.right = null;
			} else {
				root.right = rightNode;
			}
		} else if( rightNode.left == null ){
			AST temp = new AST();
			temp.value = primaryValue;
			rightNode.left = temp;
		} else if( rightNode.right == null ){
			AST temp = new AST();
			temp.value = primaryValue;
			rightNode.right = temp;
		}
		trees.push( root );
	}

	@Override
	public void enterRead_stmt(LittleParser.Read_stmtContext ctx) {
		AST root = new AST();
		root.value = "READ " + (ctx.id_list().getText());
		trees.push(root);
	}

	@Override
	public void enterWrite_stmt(LittleParser.Write_stmtContext ctx) {
		AST root = new AST();
		root.value = "WRITE " + (ctx.id_list().getText());
		trees.push(root);
	}

	public Stack<AST> getASTs(){
		return trees;
	}

	/*Print tree in post order*/
	public void printAST(){
		for(AST tree: trees){
			ArrayList<String> output = postOrderIterative(tree);

			//Post Order traversal is
			System.out.println(output);
		}
	}

    ArrayList<String> postOrderIterative(AST node)
    {
        Stack<AST> S = new Stack<AST>();
		ArrayList<String> list = new ArrayList<String>();

        // Check for empty tree
        if (node == null)
            return list;
        S.push(node);
        AST prev = null;
        while (!S.isEmpty())
        {
            AST current = S.peek();

            /* go down the tree in search of a leaf an if so process it
            and pop stack otherwise move down */
            if (prev == null || prev.left == current ||
                                        prev.right == current)
            {
                if (current.left != null)
                    S.push(current.left);
                else if (current.right != null)
                    S.push(current.right);
                else
                {
                    S.pop();
                    list.add(current.value);
                }

                /* go up the tree from left node, if the child is right
                push it onto stack otherwise process parent and pop
                stack */
            }
            else if (current.left == prev)
            {
                if (current.right != null)
                    S.push(current.right);
                else
                {
                    S.pop();
                    list.add(current.value);
                }

                /* go up the tree from right node and after coming back
                from right node process parent and pop stack */
            }
            else if (current.right == prev)
            {
                S.pop();
                list.add(current.value);
            }

            prev = current;
        }

        return list;
    }

	/*Generates IR Code from the AST*/
	public void IRCodeFactory(){
		IRCode.push(new CodeObject(";IR code\n;LABEL main\n;LINK", "", ""));
		for( AST tree: trees ){
			Stack<AST> S = new Stack<AST>();

			// Check for empty tree
			if (tree == null)
				return;
			S.push(tree);
			AST prev = null;
			while (!S.isEmpty())
			{
				AST current = S.peek();

				/* go down the tree in search of a leaf an if so process it
				and pop stack otherwise move down */
				if (prev == null || prev.left == current ||
											prev.right == current)
				{
					if (current.left != null)
						S.push(current.left);
					else if (current.right != null)
						S.push(current.right);
					else
					{
						S.pop();
						convertIRCode(current.value);
					}

					/* go up the tree from left node, if the child is right
					push it onto stack otherwise process parent and pop
					stack */
				}
				else if (current.left == prev)
				{
					if (current.right != null)
						S.push(current.right);
					else
					{
						S.pop();
						convertIRCode(current.value);
					}

					/* go up the tree from right node and after coming back
					from right node process parent and pop stack */
				}
				else if (current.right == prev)
				{
					S.pop();
					convertIRCode(current.value);
				}

				prev = current;
			}
		}
		IRCode.push(new CodeObject("\n;RET\n;tiny code\n", "", ""));
		for( CodeObject object: IRCode){
			System.out.print(object.toString());
		}
	}

	public void convertIRCode( String s ){
		String[] arr = s.split(" ");
		//System.out.println("TEST:" + Arrays.toString(arr));
		String code = "";
		String temp = "";
		String type = "";
		String[] allVars; //store all vars in READ and WRITE statements
		CodeObject rightSide;
		CodeObject leftSide;

		switch(arr[0]){
			case "VARREF":
				CodeObject var = new CodeObject("", arr[1], arr[2] );
				IRCode.push(var);
				break;
			case "ADDOP":
				rightSide = IRCode.pop();
				leftSide = IRCode.pop();
				temp = generateTemp();
				type = rightSide.getType();
				code = leftSide.getCode();
				code += rightSide.getCode();

				if(leftSide.getType().equals("CONSTANT")){
					code += "\n;STORE" + type.charAt(0) + " " +  leftSide.getTemp() + " " + temp;
					leftSide.temp = temp;
					temp = generateTemp();
				}
				if(rightSide.getType().equals("CONSTANT")){
					code += "\n;STORE" + type.charAt(0) + " " +  rightSide.getTemp() + " " + temp;
					rightSide.temp = temp;
					temp = generateTemp();
				}

				if(arr[1].equals("+")){
					code += "\n;" + "ADD"+ type.charAt(0) + " " + leftSide.getTemp() + " " + rightSide.getTemp() + " " + temp;
				} else if( arr[1].equals("-")){
					code += "\n;" + "SUB"+ type.charAt(0) + " " + leftSide.getTemp() + " " + rightSide.getTemp() + " " + temp;
				}
				CodeObject addExpr = new CodeObject(code, temp, type);
				IRCode.push(addExpr);
				break;
			case "MULOP":
				rightSide = IRCode.pop();
				leftSide = IRCode.pop();
				temp = generateTemp();
				type = leftSide.getType();
				code = leftSide.getCode();
				code += rightSide.getCode();

				if(leftSide.getType().equals("CONSTANT")){
					code += "\n;STORE" + type.charAt(0) + " " +  leftSide.getTemp() + " " + temp;
					leftSide.temp = temp;
					temp = generateTemp();
				}
				if(rightSide.getType().equals("CONSTANT")){
					code += "\n;STORE" + type.charAt(0) + " " +  rightSide.getTemp() + " " + temp;
					rightSide.temp = temp;
					temp = generateTemp();
				}

				if(arr[1].equals("*")){
					code += "\n;" + "MULT"+ type.charAt(0) + " " + leftSide.getTemp() + " " + rightSide.getTemp() + " " + temp;
				} else if( arr[1].equals("/")){
					code += "\n;" + "DIV"+ type.charAt(0) + " " + leftSide.getTemp() + " " + rightSide.getTemp() + " " + temp;
				}
				CodeObject mulExpr = new CodeObject(code, temp, type);
				IRCode.push(mulExpr);
				break;
			case "CONSTANT":
				CodeObject constant = new CodeObject("", arr[1], "CONSTANT");
				IRCode.push(constant);
				break;
			case ":=":
				rightSide = IRCode.pop(); //right side of :=
				leftSide = IRCode.pop(); //left side of :=
				if(rightSide.getCode().equals("")){
					if( rightSide.getType().equals("CONSTANT") ){
						temp = generateTemp();
						code = "\n;STORE" + leftSide.getType().charAt(0) + " " +  rightSide.getTemp() + " " + temp;
						code += "\n;STORE" + leftSide.getType().charAt(0) + " " + temp + " " + leftSide.getTemp();
						CodeObject simpleAssign = new CodeObject(code, "", "");
						IRCode.push(simpleAssign);
						break;
					} else if( rightSide.getType().equals("FLOAT")){
						temp = generateTemp();
						code = rightSide.getCode();
						code += "\n;STOREF " +  rightSide.getTemp() + " " + temp;
						code += "\n;STOREF " + temp + " " + leftSide.getTemp();
						CodeObject simpleAssign = new CodeObject(code, "", "");
						IRCode.push(simpleAssign);
						break;
					} else if( rightSide.getType().equals("INT") ){
						code = rightSide.getCode();
						code += "\n;STOREI " + rightSide.getTemp() + " " + leftSide.getTemp();
						CodeObject simpleAssign = new CodeObject(code, "", "");
						IRCode.push(simpleAssign);
						break;
					}
				}
				code = rightSide.getCode();
				code += "\n;STORE" + rightSide.getType().charAt(0) + " " + rightSide.getTemp() + " " + leftSide.getTemp();
				CodeObject simpleAssignEquation = new CodeObject(code, "", "");
				IRCode.push(simpleAssignEquation);
				break;
			case "READ":
				allVars = arr[1].split(",");
				code = "";
				for(String readVar: allVars){
					code += "\n;READ" + varsType.get(readVar).charAt(0) + " " + readVar;
				}
				CodeObject readObj = new CodeObject(code, "", "");
				IRCode.push(readObj);
				break;
			case "WRITE":
				allVars = arr[1].split(",");
				code = "";
				for( String writeVar: allVars){
					code += "\n;WRITE" + varsType.get(writeVar).charAt(0) + " " + writeVar;
				}
				CodeObject writeObj = new CodeObject(code, "", "");
				IRCode.push(writeObj);
				break;
		}
	}

	public void tinyCodeFactory( ){
		tempIRNum = -1; //keeps track of registers
		for(int i = 0; i < varCount; i++){
			String type = varsType.get(varsOrder.get(i));
			if( type.equals("INT") || type.equals("FLOAT")){
				tinyCode.push("var "+ varsOrder.get(i));
			} else if( type.equals("STRING")){
				String stringValue = varsType.get(varsOrder.get(i));
				tinyCode.push("str " + varsOrder.get(i) + " " + strValue.get(varsOrder.get(i)));
			}
		}
		for( CodeObject object: IRCode){
			String[] temp = object.toString().split("\n");
			for( String s: temp ){
				IRtoTinyCode(s);
			}
		}

		tinyCode.push("sys halt");

		/*Print tiny code*/
		for( String s: tinyCode ){
			System.out.println( s );
		}
	}

	public void IRtoTinyCode(String s){
		String[] arr = s.split(" ");
		String op1;
		String op2;
		String register;
		if(arr[0].startsWith(";STORE")){
			if(arr[1].startsWith("$T")){
				op1 = "r"+arr[1].replace("$T", "");
			} else {
				op1 = arr[1];
			}

			if(arr[2].startsWith("$T")){
				op2 = "r"+arr[2].replace("$T", "");
			} else {
				op2 = arr[2];
			}
			tinyCode.push("move " + op1 + " " + op2);
		}
		else if(arr[0].equals(";READI")){
			tinyCode.push( "sys readi " + arr[1]);
		}
		else if(arr[0].equals(";READF")){
			tinyCode.push( "sys readr " + arr[1]);
		}
		else if(arr[0].equals(";WRITEI")){
			tinyCode.push( "sys writei " + arr[1]);
		}
		else if(arr[0].equals(";WRITEF")){
			tinyCode.push( "sys writer " + arr[1]);
		}
		else if(arr[0].equals(";WRITES")){
			tinyCode.push( "sys writes " + arr[1]);
		}
		else if(arr[0].equals(";MULTI")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("muli " + arr[2] + register);
		}
		else if(arr[0].equals(";MULTF")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("mulr " + arr[2] + register);
		}
		else if(arr[0].equals(";DIVI")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("divi " + arr[2] + register);
		}
		else if(arr[0].equals(";DIVF")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}

			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("divr " + arr[2] + register);
		}
		else if(arr[0].equals(";ADDI")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("addi " + arr[2] + register);
		}
		else if(arr[0].equals(";ADDF")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("addr " + arr[2] + register);
		}
		else if(arr[0].equals(";SUBI")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("subi " + arr[2] + register);
		}
		else if(arr[0].equals(";SUBF")){
			register = " r" + arr[3].replace("$T", "");
			if(arr[1].startsWith("$T")){
				arr[1] = "r"+arr[1].replace("$T", "");
			}
			if(arr[2].startsWith("$T")){
				arr[2] = "r"+arr[2].replace("$T", "");
			}
			tinyCode.push("move " + arr[1] + register );
			tinyCode.push("subr " + arr[2] + register);
		}
	}

	/*Generates temp for IR Code representation*/
	public String generateTemp(){
		tempIRNum++;
		return "$T" + tempIRNum;
	}
}
