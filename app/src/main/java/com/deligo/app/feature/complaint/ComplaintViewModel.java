package com.deligo.app.feature.complaint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deligo.app.core.util.Result;
import com.deligo.app.data.repo.ComplaintRepository;

public class ComplaintViewModel extends ViewModel {
    private final ComplaintRepository complaintRepository;

    public ComplaintViewModel(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public LiveData<Result<Void>> submitComplaint(long orderId, String message) {
        return complaintRepository.submitComplaint(orderId, message);
    }
}
