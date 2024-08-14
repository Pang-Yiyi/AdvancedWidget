package com.example.midterm;

public class material {
    public String name;
    public int amount;
    public String content;
    public String imageName; // 新增圖片名稱屬性

    public material(String name, int amount, String content, String imageName){
        this.name = name;
        this.amount = amount;
        this.content = content;
        this.imageName = imageName;
    }

    // getter 和 setter 方法
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
}

