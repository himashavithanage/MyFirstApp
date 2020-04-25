package com.codeComplexity.service;

import java.util.List;

import com.codeComplexity.model.SingleLineMethods;
import com.codeComplexity.util.CommonConstants;

public class ComplexityMethodsService {
	public List<SingleLineMethods> calculateComplexityDueToMethods(List<SingleLineMethods> statementList) throws Exception{
	
		CommonConstants common = new CommonConstants();
		List<String> dtypePrimListCommons = common.getPrimitiveList();
		
		int count = 1;
		for(SingleLineMethods line : statementList) {
			//Each line
			
			
			//Check whether this is a method or not
			if(line.getStatement().contains("public") && line.getStatement().contains("(") && line.getStatement().contains(")")){
				System.out.println("\nThis is a method");
				
				String a[] = line.getStatement().split(" ");
				
				//set wmrt
				int var1 = 0;
				for(int i = 0; i < a.length; i++) {
					if(a[i].contains("public")) {
						var1=i;
					}
				}
				
				if(a[var1+1].contains("static")) {
					System.out.println("Static method");
					//Check for return type
					
					boolean voidT = false;
					boolean primT = false;
					for(String typeD : dtypePrimListCommons) {
						if(a[var1+2].contains("void")) {
							voidT = true;
						}
						else if(a[var1+2].contains(typeD)) {
							primT = true;
						}
					}
					
					if(voidT == true) {
						line.setWmrt(0);
						System.out.println("Void method");
					}
					else if(primT == true) {
						line.setWmrt(1);
					}
					else if(voidT == false && primT == false) {
						line.setWmrt(2);
					}
					
				}
				else if(a[var1+1].contains("void")) {
					System.out.println("Void method");
					line.setWmrt(0);
				}
				else {
					System.out.println("Normal method");
					//Check for return type
					boolean primT = false;
					for(String typeD : dtypePrimListCommons) {
						if(a[var1+1].contains(typeD)) {
							primT = true;
						}
					}
					
					if(primT == true) {
						line.setWmrt(1);
					}
					else {
						line.setWmrt(2);
					}
				}
				
				//set Other parameters
				//Have to check for parameters
				int index001 = line.getStatement().indexOf("(");
				int index002 = line.getStatement().indexOf(")");
				String subStr = line.getStatement().substring(index001+1, index002);
				System.out.println("sbstring: " + subStr);
				
				if(subStr.equals("") || subStr.equals(" ")) {
					line.setNpdtp(0);
					line.setNcdtp(0);
				}
				else {
					if(subStr.contains(",")) {
						//Contains 2 or more parameters
						System.out.println("Contains 2 or more parameters");
						
						String splitComma[]  = subStr.split(",");
						for(int var2 = 0; var2 < splitComma.length; var2++) {
							System.out.println("splicomma => " + splitComma[var2]);
						}
						
						//Hariharan Working fine
						int primCount = 0;
						for(int var3 = 0; var3 < splitComma.length; var3++) {
							//Primitive check
							System.out.println("Primitive check");
							if(splitComma[var3].contains("boolean") || splitComma[var3].contains("byte") || splitComma[var3].contains("char") || splitComma[var3].contains("short") || splitComma[var3].contains("int") || splitComma[var3].contains("long") || splitComma[var3].contains("float") || splitComma[var3].contains("double")) {
								primCount++;
							}
						}
						
						line.setNpdtp(primCount);
						line.setNcdtp(splitComma.length - primCount);
					}
					else {
						boolean primStatus002 = false;
						for(String dtypev01 : common.getPrimitiveList()) {
							if(subStr.contains(dtypev01)) {
								primStatus002 = true;
							}
						}
						
						if(primStatus002 == true) {
							line.setNpdtp(1);
						}
						else {
							line.setNcdtp(1);
						}
					}
				}
				}
			
			line.calculateCM();
			
			System.out.println("Line "+ count +" : " + line.getStatement() + "\n");
			count++;
		}
		
		return statementList;
	}
}