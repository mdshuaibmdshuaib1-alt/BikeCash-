package com.example.data

class PriceEstimationService {
    fun estimatePrice(submission: BikeSubmission): PriceEstimate {
        // Transparent baseline: Placeholder for future API/dataset integration.
        // Currently returns "Unable to estimate" as per instructions.
        return PriceEstimate(
            message = "Unable to estimate reliably yet due to insufficient market data.",
            status = "indicative"
        )
    }
}
