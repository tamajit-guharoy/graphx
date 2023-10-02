package com.tamajit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;

public class AddMethodExample {
  public static void main(String[] args) throws Exception {
    // Step 1: Read the existing Java file
    FileInputStream in = new FileInputStream("YourExistingFile.java");
    ParseResult<CompilationUnit> parseResult = new JavaParser().parse(in);
    in.close();

    // Retrieve the parsed compilation unit or handle parsing errors
    if (parseResult.isSuccessful()) {
      CompilationUnit cu = parseResult.getResult().get();

      // Step 2: Modify the AST - Add a new method
      Type returnType = PrimitiveType.intType();
      String methodName = "newMethod";
      Modifier publicModifier = Modifier.publicModifier();
      MethodDeclaration method = new MethodDeclaration();
      NodeList list=new NodeList();
      list.add(publicModifier);
      method.setModifiers(list);
      method.setType(returnType);
      method.setName(methodName);
      method.addParameter(new Parameter(PrimitiveType.intType(), "param1"));
      method.setBody(null);

      cu.getClassByName("YourExistingFile")
        .orElseThrow(() -> new IllegalArgumentException("Class not found"))
        .addMember(method);

      // Step 3: Generate code from AST
      String updatedCode = cu.toString();

      // Step 4: Write the updated code to the file
      FileOutputStream out = new FileOutputStream("YourExistingFile.java");
      out.write(updatedCode.getBytes());
      out.close();
    } else {
      // Handle parsing errors
      System.out.println("Parsing failed: " + parseResult.getProblems());
    }
  }
}
