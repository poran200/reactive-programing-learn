package com.learnractive.reactive.fluxandmonoplayground;

public class CustomExeption extends Throwable {
    private String massage;
    public CustomExeption(Throwable e) {
        this.massage = e.getMessage();
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */

    @Override
    public String getMessage() {
        return massage;
    }
}
