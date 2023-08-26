package com.mediscreen.massessment.proxies;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "history", url = "https://localhost:4200/data")
public interface PatientHistoryApiProxy {
}
