package com.finalproject.mymoneyapi.model;

public class UserData {

    private SimpleUser user;
    private double totalDespesas;
    private int qtdDespesas;
    private double totalReceitas;
    private int qtdReceitas;

    public UserData(SimpleUser user, double[] totais, int[] quantidades) {
        this.user = user;
        this.totalDespesas = totais[0];
        this.totalReceitas = totais[1];

        this.qtdDespesas = quantidades[0];
        this.qtdReceitas = quantidades[1];
    }

    public SimpleUser getUser() {
        return user;
    }

    public void setUser(SimpleUser user) {
        this.user = user;
    }

    public double getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(double totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public int getQtdDespesas() {
        return qtdDespesas;
    }

    public void setQtdDespesas(int qtdDespesas) {
        this.qtdDespesas = qtdDespesas;
    }

    public double getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(double totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public int getQtdReceitas() {
        return qtdReceitas;
    }

    public void setQtdReceitas(int qtdReceitas) {
        this.qtdReceitas = qtdReceitas;
    }
}
