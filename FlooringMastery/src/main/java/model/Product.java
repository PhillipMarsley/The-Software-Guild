/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Dan
 */
public class Product {

    private String type;

    private BigDecimal costPerSqFtMaterial;
    private BigDecimal costPerSqFtLabor;

    public Product() {
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public BigDecimal getCostPerSqFtMaterial() {
	return costPerSqFtMaterial;
    }

    public void setCostPerSqFtMaterial(BigDecimal costPerSqFtMaterial) {
	this.costPerSqFtMaterial = costPerSqFtMaterial;
    }

    public BigDecimal getCostPerSqFtLabor() {
	return costPerSqFtLabor;
    }

    public void setCostPerSqFtLabor(BigDecimal costPerSqFtLabor) {
	this.costPerSqFtLabor = costPerSqFtLabor;
    }
}
