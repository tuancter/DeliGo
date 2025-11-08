package com.deligo.app.data.repo;

import androidx.lifecycle.LiveData;

import com.deligo.app.core.util.Result;

public interface ComplaintRepository {
    LiveData<Result<Void>> submitComplaint(long orderId, String message);
}
