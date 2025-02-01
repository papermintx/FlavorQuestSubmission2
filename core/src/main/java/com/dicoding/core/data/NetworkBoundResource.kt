package com.dicoding.core.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow {
        val dbSource = loadFromDb().first()

        if (shouldFetch(dbSource)) {
            Log.d(TAG, "asFlow: shouldFetch: $dbSource")
            emit(Resource.Loading(dbSource))

            try {
                Log.d(TAG, "asFlow: fetchFromNetwork")
                fetchFromNetwork().collect { response ->
                    saveNetworkResult(response)
                    Log.d(TAG, "asFlow: fetchFromNetwork: response: $response")
                    loadFromDb().collect { dbResponse ->
                        Log.d(TAG, "asFlow: fetchFromNetwork: dbResponse: $dbResponse")
                        emit(Resource.Success(dbResponse))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "asFlow: Error fetching data from network", e)
                emit(Resource.Error(e, dbSource))
            }
        } else {
            Log.d(TAG, "asFlow: loadFromDb")
            emit(Resource.Success(dbSource))
        }
    }

    protected abstract suspend fun fetchFromNetwork(): Flow<RequestType>

    protected abstract suspend fun saveNetworkResult(item: RequestType)

    protected abstract fun loadFromDb(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean


    companion object {
        private const val TAG = "NetworkBoundResource"
    }
}