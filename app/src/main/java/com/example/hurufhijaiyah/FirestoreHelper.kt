package com.example.hurufhijaiyah

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirestoreHelper {

    private val db = FirebaseFirestore.getInstance()
    private val usersCol = db.collection("users")

    // ===================================================
    // 🔹 INISIALISASI — Seed akun admin default
    // ===================================================

    fun seedAdminAccount() {
        val adminDoc = usersCol.document("admin")
        adminDoc.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                val adminData = hashMapOf(
                    "nama_lengkap" to "Administrator",
                    "password" to "admin123",
                    "role" to "admin",
                    "skor" to 0,
                    "total_quiz" to 0,
                    "highest_score" to 0
                )
                adminDoc.set(adminData)
            }
        }
    }

    // ===================================================
    // 🔹 FUNGSI LOGIN & AUTH
    // ===================================================

    fun loginUser(username: String, password: String, callback: (Boolean) -> Unit) {
        usersCol.document(username).get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.getString("password") == password) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { callback(false) }
    }

    fun getUserRole(username: String, callback: (String) -> Unit) {
        usersCol.document(username).get()
            .addOnSuccessListener { doc ->
                val role = doc.getString("role") ?: "murid"
                callback(role)
            }
            .addOnFailureListener { callback("murid") }
    }

    fun getNamaLengkap(username: String, callback: (String) -> Unit) {
        usersCol.document(username).get()
            .addOnSuccessListener { doc ->
                val nama = doc.getString("nama_lengkap") ?: username
                callback(nama)
            }
            .addOnFailureListener { callback(username) }
    }

    // ===================================================
    // 🔹 FUNGSI QUIZ STATS
    // ===================================================

    fun updateQuizStats(username: String, skorQuiz: Int) {
        val docRef = usersCol.document(username)
        docRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                val totalQuiz = (doc.getLong("total_quiz")?.toInt() ?: 0) + 1
                val currentHighest = doc.getLong("highest_score")?.toInt() ?: 0
                val newHighest = if (skorQuiz > currentHighest) skorQuiz else currentHighest

                docRef.update(
                    mapOf(
                        "total_quiz" to totalQuiz,
                        "highest_score" to newHighest
                    )
                )
            }
        }
    }

    fun getQuizStats(username: String, callback: (Pair<Int, Int>) -> Unit) {
        usersCol.document(username).get()
            .addOnSuccessListener { doc ->
                val totalQuiz = doc.getLong("total_quiz")?.toInt() ?: 0
                val highestScore = doc.getLong("highest_score")?.toInt() ?: 0
                callback(Pair(totalQuiz, highestScore))
            }
            .addOnFailureListener { callback(Pair(0, 0)) }
    }

    // ===================================================
    // 🔹 FUNGSI SKOR
    // ===================================================

    fun updateSkor(username: String, skor: Int) {
        usersCol.document(username).update("skor", skor)
    }

    fun getSkor(username: String, callback: (Int) -> Unit) {
        usersCol.document(username).get()
            .addOnSuccessListener { doc ->
                val skor = doc.getLong("skor")?.toInt() ?: 0
                callback(skor)
            }
            .addOnFailureListener { callback(0) }
    }

    // ===================================================
    // 🔹 FUNGSI RIWAYAT BELAJAR (WRONG ANSWERS)
    // ===================================================

    fun addWrongAnswer(username: String, hurufArab: String, hurufLatin: String) {
        val wrongDoc = usersCol.document(username)
            .collection("wrong_answers")
            .document(hurufArab)

        wrongDoc.set(
            hashMapOf(
                "huruf_arab" to hurufArab,
                "huruf_latin" to hurufLatin
            )
        )
    }

    fun removeWrongAnswer(username: String, hurufArab: String) {
        usersCol.document(username)
            .collection("wrong_answers")
            .document(hurufArab)
            .delete()
    }

    fun getWrongAnswers(username: String, callback: (List<Huruf>) -> Unit) {
        usersCol.document(username)
            .collection("wrong_answers")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.map { doc ->
                    Huruf(
                        arab = doc.getString("huruf_arab") ?: "",
                        latin = doc.getString("huruf_latin") ?: ""
                    )
                }
                callback(list)
            }
            .addOnFailureListener { callback(emptyList()) }
    }

    // ===================================================
    // 🔹 FUNGSI GURU — Daftar Murid
    // ===================================================

    fun getAllMurid(callback: (List<User>) -> Unit) {
        usersCol.whereEqualTo("role", "murid").get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.map { doc ->
                    User(
                        username = doc.id,
                        namaLengkap = doc.getString("nama_lengkap") ?: "",
                        role = doc.getString("role") ?: "murid",
                        totalQuiz = doc.getLong("total_quiz")?.toInt() ?: 0,
                        highestScore = doc.getLong("highest_score")?.toInt() ?: 0
                    )
                }
                callback(list)
            }
            .addOnFailureListener { callback(emptyList()) }
    }

    fun getLeaderboard(callback: (List<User>) -> Unit) {
        usersCol.whereEqualTo("role", "murid")
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.map { doc ->
                    User(
                        username = doc.id,
                        namaLengkap = doc.getString("nama_lengkap") ?: "",
                        role = doc.getString("role") ?: "murid",
                        totalQuiz = doc.getLong("total_quiz")?.toInt() ?: 0,
                        highestScore = doc.getLong("highest_score")?.toInt() ?: 0
                    )
                }.sortedByDescending { it.highestScore }
                callback(list)
            }
            .addOnFailureListener { e ->
                android.util.Log.e("FirestoreLeaderboard", "Error: ${e.message}")
                callback(emptyList()) 
            }
    }

    // ===================================================
    // 🔹 FUNGSI ADMIN — Kelola User
    // ===================================================

    fun getAllUsers(callback: (List<User>) -> Unit) {
        usersCol.get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents
                    .filter { it.getString("role") != "admin" }
                    .map { doc ->
                        User(
                            username = doc.id,
                            namaLengkap = doc.getString("nama_lengkap") ?: "",
                            role = doc.getString("role") ?: "murid",
                            totalQuiz = doc.getLong("total_quiz")?.toInt() ?: 0,
                            highestScore = doc.getLong("highest_score")?.toInt() ?: 0
                        )
                    }
                callback(list)
            }
            .addOnFailureListener { callback(emptyList()) }
    }

    fun createUserByAdmin(
        namaLengkap: String,
        username: String,
        password: String,
        role: String,
        callback: (Boolean) -> Unit
    ) {
        val docRef = usersCol.document(username)
        docRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                // Username sudah ada
                callback(false)
            } else {
                val userData = hashMapOf(
                    "nama_lengkap" to namaLengkap,
                    "password" to password,
                    "role" to role,
                    "skor" to 0,
                    "total_quiz" to 0,
                    "highest_score" to 0
                )
                docRef.set(userData)
                    .addOnSuccessListener { callback(true) }
                    .addOnFailureListener { callback(false) }
            }
        }.addOnFailureListener { callback(false) }
    }

    fun deleteUser(username: String, callback: (Boolean) -> Unit) {
        val docRef = usersCol.document(username)

        // Hapus sub-collection wrong_answers dulu
        docRef.collection("wrong_answers").get()
            .addOnSuccessListener { snapshot ->
                val batch = db.batch()
                for (doc in snapshot.documents) {
                    batch.delete(doc.reference)
                }
                // Hapus dokumen user utama
                batch.delete(docRef)
                batch.commit()
                    .addOnSuccessListener { callback(true) }
                    .addOnFailureListener { callback(false) }
            }
            .addOnFailureListener { callback(false) }
    }

    fun resetStatistik(username: String, callback: (Boolean) -> Unit) {
        val docRef = usersCol.document(username)

        // Reset field statistik
        docRef.update(
            mapOf(
                "skor" to 0,
                "total_quiz" to 0,
                "highest_score" to 0
            )
        ).addOnSuccessListener {
            // Hapus semua wrong_answers
            docRef.collection("wrong_answers").get()
                .addOnSuccessListener { snapshot ->
                    val batch = db.batch()
                    for (doc in snapshot.documents) {
                        batch.delete(doc.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener { callback(true) }
                        .addOnFailureListener { callback(false) }
                }
                .addOnFailureListener { callback(false) }
        }.addOnFailureListener { callback(false) }
    }

    // ===================================================
    // 🔹 FUNGSI STATISTIK GLOBAL
    // ===================================================

    fun getTotalMurid(callback: (Int) -> Unit) {
        usersCol.whereEqualTo("role", "murid").get()
            .addOnSuccessListener { snapshot -> callback(snapshot.size()) }
            .addOnFailureListener { callback(0) }
    }

    fun getTotalGuru(callback: (Int) -> Unit) {
        usersCol.whereEqualTo("role", "guru").get()
            .addOnSuccessListener { snapshot -> callback(snapshot.size()) }
            .addOnFailureListener { callback(0) }
    }

    fun getTotalQuizDikerjakan(callback: (Int) -> Unit) {
        usersCol.whereEqualTo("role", "murid").get()
            .addOnSuccessListener { snapshot ->
                var total = 0
                for (doc in snapshot.documents) {
                    total += doc.getLong("total_quiz")?.toInt() ?: 0
                }
                callback(total)
            }
            .addOnFailureListener { callback(0) }
    }

    fun getRataRataSkor(callback: (Double) -> Unit) {
        usersCol.whereEqualTo("role", "murid").get()
            .addOnSuccessListener { snapshot ->
                val muridDenganQuiz = snapshot.documents.filter {
                    (it.getLong("total_quiz")?.toInt() ?: 0) > 0
                }
                if (muridDenganQuiz.isEmpty()) {
                    callback(0.0)
                } else {
                    val totalSkor = muridDenganQuiz.sumOf {
                        it.getLong("highest_score")?.toInt() ?: 0
                    }
                    callback(totalSkor.toDouble() / muridDenganQuiz.size)
                }
            }
            .addOnFailureListener { callback(0.0) }
    }
}
