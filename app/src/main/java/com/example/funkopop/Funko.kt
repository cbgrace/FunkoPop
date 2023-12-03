package com.example.funkopop

import org.json.JSONObject


class Funko {
    var name: String = ""
    var group: String = ""
    var number: Int = 0
    var price: Double = 0.0
    var size: Int = 0

    constructor(name: String, group: String, number: Int, price: Double, size: Int) {
        this.name = name
        this.group = group
        this.number = number
        this.price = price
        this.size = size
    }

    constructor(json: JSONObject) {
        this.name = json.getString("name")
        this.group = json.getString("group")
        this.number = json.getInt("number")
        this.price = json.getDouble("price")
        this.size = json.getInt("size")
    }

    constructor() {}

    public fun toJSON() : JSONObject {
        val json = JSONObject()
        json.put("name", this.name)
        json.put("group", this.group)
        json.put("number", this.number)
        json.put("price", this.price)
        json.put("size", this.size)
        return json
    }

}