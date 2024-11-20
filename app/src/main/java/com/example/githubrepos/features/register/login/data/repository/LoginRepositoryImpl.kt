package com.example.githubrepos.features.register.login.data.repository

import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.common.utils.await
import com.example.githubrepos.features.register.login.domain.repository.ILoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : ILoginRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        auth.signOut()
    }
}









