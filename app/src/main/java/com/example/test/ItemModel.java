package com.example.test;

import androidx.annotation.NonNull;

import java.util.Objects;

//Класс для сохранения данных из таблиц Status, Group, Component и Rhesus
//Т.к они имеют похожую структуру (Id и Название)
public class ItemModel {
    private int itemModelId;
    private String itemModelName;
    public ItemModel(int itemModelId, String itemModelName){
        this.itemModelId = itemModelId;
        this.itemModelName = itemModelName;
    }
    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
    public String getItemModelName() {
        return itemModelName;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelName(String itemModelName) {
        this.itemModelName = itemModelName;
    }

    @NonNull
    @Override
    public String toString() {
        return itemModelName;
    }
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() != object.getClass()){
            return false;
        }
        ItemModel itemModel = (ItemModel) object;
        return itemModelId == itemModel.itemModelId &&
                    itemModelName.equals(itemModel.itemModelName);
    }
    @Override
    public int hashCode(){
        return Objects.hash(itemModelId, itemModelName);
    }
}
