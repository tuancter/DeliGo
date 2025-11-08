package com.deligo.app.data.repoimpl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.api.ComplaintApi;
import com.deligo.app.data.api.dto.ComplaintRequest;
import com.deligo.app.data.repo.ComplaintRepository;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplaintRepositoryImpl implements ComplaintRepository {
    private final ComplaintApi complaintApi;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ComplaintRepositoryImpl(ComplaintApi complaintApi) {
        this.complaintApi = complaintApi;
    }

    @Override
    public LiveData<Result<Void>> submitComplaint(long orderId, String message) {
        MutableLiveData<Result<Void>> liveData = new MutableLiveData<>();
        liveData.setValue(Result.loading(null));
        executor.execute(() -> {
            try {
                complaintApi.submitComplaint(new ComplaintRequest(orderId, message)).execute();
                liveData.postValue(Result.success(null));
            } catch (IOException e) {
                liveData.postValue(Result.error(e.getMessage(), null));
            }
        });
        return liveData;
    }
}
