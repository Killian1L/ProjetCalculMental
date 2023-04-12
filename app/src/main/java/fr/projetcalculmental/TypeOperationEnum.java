package fr.projetcalculmental;

public enum TypeOperationEnum {

    ADD(" + "),
    MULTIPLY(" * "),
    SUBSTRACT(" - "),
    DIVIDE(" / ");

    private String symbol;

    TypeOperationEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
