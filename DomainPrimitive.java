package org.DDD;

import java.util.List;

public class DomainPrimitive implements Builder {
    private final String className;
    private final List<String> variables;
    private final List<String> variableTypes;

    public DomainPrimitive(DomainPrimitiveBuilder builder) {
        this.className = builder.className;
        this.variables = builder.variables;
        this.variableTypes = builder.variableTypes;
    }



    public String addID() {
        return "\t\n" +
                "\n\t// Getter method for " + className + "Id" + "\n" +
                "\tpublic " + "Long " + className + "Id" + "() {\n" +
                "\t\treturn " + deCapitalize(className) + "Id" + ";\n" +
                "\t}\n";
    }

    @Override
    public String generateGet() {
        StringBuilder getCode = new StringBuilder();

        // Generate getter methods for each variable
        for (int i = 0; i < 1; i++) {
            // Construct the getter method name
            String variable = variables.get(i);
            String getterName = "get" + variable.substring(0, 1).toUpperCase() + variable.substring(1);

            // Append the getter method definition
            getCode.append("\n\t// Getter method for ").append(variable).append("\n");
            getCode.append("\tpublic ").append(variableTypes.get(variables.indexOf(variable))).append(" ").append(getterName).append("() {\n");
            getCode.append("\t\treturn ").append(variable).append(";\n");
            getCode.append("\t}\n");
        }


        return getCode.toString();
    }

    @Override
    public String generateConstructor() {
        StringBuilder constructorCode = new StringBuilder();

        // Append the constructor signature
        constructorCode.append("\tpublic ").append(capitalize(className)).append("(");
        // Append parameters for constructor
        for (int i = 0; i < 1; i++) {
            constructorCode.append(variableTypes.get(i)).append(" ").append(variables.get(i));
            constructorCode.append(") {\n");
            constructorCode.append("\t\tthis.").append(variables.get(i)).append(" = ").append(variables.get(i)).append(";\n");
        }

        // Append the constructor body


        for (String variable : variables) {

        }
        constructorCode.append("\t}\n");

        return constructorCode.toString();
    }

    @Override
    public String generatetoString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\t@Override\n");
        sb.append("\tpublic String toString() {\n");
        sb.append("\t\treturn ");
        // Append each variable with its corresponding value
        for (int i = 0; i < 1; i++) {
            String variable = variables.get(i);
            sb.append(variable).append("='").append((variable)).append("'");
        }

        sb.append(";\n");
        sb.append("\t}\n");
        return sb.toString();
    }

    @Override
    public String generateHashCode() {
        StringBuilder hashCodeCode = new StringBuilder();

        hashCodeCode.append("\t@Override\n");
        hashCodeCode.append("\tpublic int hashCode() {\n");
        hashCodeCode.append("\t\treturn Objects.hash(");

        for (int i = 0; i < 1; i++) {
            hashCodeCode.append(variables.get(i));
        }

        hashCodeCode.append(");\n");
        hashCodeCode.append("\t}\n");

        return hashCodeCode.toString();
    }



    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    private String deCapitalize(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
}





