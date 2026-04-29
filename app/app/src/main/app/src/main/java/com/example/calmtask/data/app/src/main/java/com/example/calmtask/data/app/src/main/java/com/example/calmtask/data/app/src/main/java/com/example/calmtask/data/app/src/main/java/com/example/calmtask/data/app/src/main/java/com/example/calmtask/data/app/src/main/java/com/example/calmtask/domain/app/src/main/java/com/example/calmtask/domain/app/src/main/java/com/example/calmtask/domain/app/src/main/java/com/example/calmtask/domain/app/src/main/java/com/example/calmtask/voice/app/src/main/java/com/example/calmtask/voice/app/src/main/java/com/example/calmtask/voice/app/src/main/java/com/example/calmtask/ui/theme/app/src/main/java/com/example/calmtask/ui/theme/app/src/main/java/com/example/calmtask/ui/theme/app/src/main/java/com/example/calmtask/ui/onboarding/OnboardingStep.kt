package com.example.calmtask.ui.onboarding

// Total: 10 steps  (4 new profile steps + 6 original steps)
enum class OnboardingStep {
    // ── NEW PROFILE STEPS ──────────────────────────────
    GENDER,       // Step 1 — Select Male / Female
    LANGUAGE,     // Step 2 — Select Language
    COUNTRY,      // Step 3 — Select Country
    AGE,          // Step 4 — Select Age Range

    // ── ORIGINAL STEPS ────────────────────────────────
    WELCOME,      // Step 5
    HOW_IT_WORKS, // Step 6
    TRY_COMMAND,  // Step 7
    YOUR_NAME,    // Step 8
    PERMISSIONS,  // Step 9
    ALL_SET       // Step 10
}
