package com.example.adapterviewxpath

class Saint(var name: String, var dob: String, var dod: String, rating: Float) : Comparable<Saint> {
    var rating = 0f

    init {
        this.rating = rating
    }

    override fun compareTo(other: Saint): Int {
        return name.compareTo(other.name)           // сравнение святых со строками не является богохульством? :)
    }

}

//class Saint() {
//private String name;
//private String dob;
//private String dod;
//private float rating = 0f;
//}