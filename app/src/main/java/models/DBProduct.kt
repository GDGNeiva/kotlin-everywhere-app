package models

import android.content.ContentValues
import android.content.Context

class DBProduct(val context: Context) {
    private val objDBHelper = DBHelper(context, null, "product")

    fun insert(product: Product): Boolean {
        val values = ContentValues()
        values.put("code", product.code)
        values.put("name", product.name)
        values.put("price", product.price)
        values.put("img", product.img)

        return objDBHelper.insert(values)
    }

    fun update(product: Product): Boolean {
        val values = ContentValues()
        values.put("code", product.code)
        values.put("name", product.name)
        values.put("price", product.price)
        values.put("img", product.img)

        return objDBHelper.update(values, product.id)
    }

    fun delete(id: Int): Boolean {
        return objDBHelper.delete(id)
    }

    fun select(): ArrayList<Product> {
        val curData = objDBHelper.select("SELECT * FROM product")
        val products: ArrayList<Product> = ArrayList()
        if (! curData!!.moveToFirst()) {
            return products
        }

        do {
            products.add(Product(
                curData.getInt(curData.getColumnIndex("id")),
                curData.getString(curData.getColumnIndex("code")),
                curData.getInt(curData.getColumnIndex("img")),
                curData.getString(curData.getColumnIndex("name")),
                curData.getInt(curData.getColumnIndex("price"))
            ));
        } while (curData.moveToNext())
        curData.close()
        return products
    }

    fun selectById(id: Int): Product {
        val curData = objDBHelper.select("SELECT * FROM product WHERE id = $id")
        curData!!.moveToFirst()
        val product = Product(
            curData.getInt(curData.getColumnIndex("id")),
            curData.getString(curData.getColumnIndex("code")),
            curData.getInt(curData.getColumnIndex("img")),
            curData.getString(curData.getColumnIndex("name")),
            curData.getInt(curData.getColumnIndex("price"))
        )
        curData.close()
        return product
    }
}