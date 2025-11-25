# TODO for Blood Donation App Development

## Step 1: Update build.gradle.kts ✅
- Remove Compose plugins and dependencies
- Add dependencies for Retrofit, Gson, ViewModel, LiveData, RecyclerView, CardView, Material Components, etc.

## Step 2: Update AndroidManifest.xml ✅
- Add INTERNET permission
- Change launcher activity to RoleSelectionActivity
- Register all activities

## Step 3: Update strings.xml ✅
- Add strings for UI elements (buttons, labels, toasts, etc.)

## Step 4: Create Model ✅
- Create Donor.kt data class

## Step 5: Create Retrofit Setup ✅
- Create ApiService.kt
- Create ApiClient.kt

## Step 6: Create Repository ✅
- Create DonorRepository.kt

## Step 7: Create ViewModels ✅
- Create DonorViewModel.kt
- Create RecipientViewModel.kt

## Step 8: Create Activities and Layouts ✅
- Create RoleSelectionActivity.kt and activity_role_selection.xml
- Create DonorRegistrationActivity.kt and activity_donor_registration.xml
- Create RecipientSearchActivity.kt and activity_recipient_search.xml

## Step 9: Create Adapter and Item Layout ✅
- Create DonorAdapter.kt
- Create donor_item.xml

## Step 10: Finalize and Test ✅
- Remove or update MainActivity.kt
- Provide final folder structure
- Ensure all code is complete and functional
