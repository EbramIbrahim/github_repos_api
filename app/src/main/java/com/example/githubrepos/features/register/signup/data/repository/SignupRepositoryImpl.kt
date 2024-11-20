package com.example.githubrepos.features.register.signup.data.repository

import com.example.githubrepos.common.utils.Resource
import com.example.githubrepos.common.utils.await
import com.example.githubrepos.features.register.signup.domain.repository.ISignupRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.lang.Exception
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : ISignupRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signup(email: String, password: String, name: String): Resource<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}









