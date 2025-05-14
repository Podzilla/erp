// package com.Podzilla.analytics.controllers;

// import com.Podzilla.analytics.api.dtos.RevenueSummaryResponse;
// import com.Podzilla.analytics.api.controllers.RevenueReportController;
// import com.Podzilla.analytics.api.dtos.RevenueSummaryRequest.Period;
// import com.Podzilla.analytics.services.RevenueReportService;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.Collections;
// import java.util.List;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.hamcrest.Matchers.*; // For jsonPath matchers

// @WebMvcTest(RevenueReportController.class) // Specify the controller to test
// class RevenueReportControllerTest {

//     @Autowired
//     private MockMvc mockMvc; // Injected for performing HTTP requests

//     @MockBean // Create a mock instance of the service dependency
//     private RevenueReportService revenueReportService;

//     // Helper method to create a valid URL with parameters
//     private String buildSummaryUrl(LocalDate startDate, LocalDate endDate, Period period) {
//         return String.format("/revenue/summary?startDate=%s&endDate=%s&period=%s",
//                 startDate, endDate, period);
//     }

//     @Test
//     void getRevenueSummary_ValidRequest_ReturnsOkAndSummaryList() throws Exception {
//         // Arrange: Define test data and mock service behavior
//         LocalDate startDate = LocalDate.of(2023, 1, 1);
//         LocalDate endDate = LocalDate.of(2023, 1, 31);
//         Period period = Period.MONTHLY;

//         RevenueSummaryResponse mockResponse = RevenueSummaryResponse.builder()
//                 .periodStartDate(startDate)
//                 .totalRevenue(BigDecimal.valueOf(1500.50))
//                 .build();
//         List<RevenueSummaryResponse> mockSummaryList = Collections.singletonList(mockResponse);

//         // Mock the service call - expect any RevenueSummaryRequest and return the mock list
//         when(revenueReportService.getRevenueSummary(any()))
//                 .thenReturn(mockSummaryList);

//         // Act: Perform the HTTP GET request
//         mockMvc.perform(get(buildSummaryUrl(startDate, endDate, period))
//                 .contentType(MediaType.APPLICATION_JSON)) // Although GET, setting content type is harmless
//                 .andExpect(status().isOk()) // Assert: Expect HTTP 200 OK
//                 .andExpect(jsonPath("$", hasSize(1))) // Expect a JSON array with one element
//                 .andExpect(jsonPath("$[0].periodStartDate", is(startDate.toString()))) // Check response fields
//                 .andExpect(jsonPath("$[0].totalRevenue", is(1500.50)));
//     }

//     @Test
//     void getRevenueSummary_MissingStartDate_ReturnsBadRequest() throws Exception {
//         // Arrange: Missing startDate parameter
//         LocalDate endDate = LocalDate.of(2023, 1, 31);
//         Period period = Period.MONTHLY;

//         // Act & Assert: Perform request and expect HTTP 400 Bad Request due to @NotNull
//         mockMvc.perform(get("/revenue/summary?endDate=" + endDate + "&period=" + period)
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isBadRequest());
//         // You could add more assertions here to check the response body for validation error details
//     }

// //      @Test
// //     void getRevenueSummary_EndDateBeforeStartDate_ReturnsBadRequest() throws Exception {
// //         // Arrange: Invalid date range (endDate before startDate) - testing @AssertTrue
// //         LocalDate startDate = LocalDate.of(2023, 1, 31);
// //         LocalDate endDate = LocalDate.of(2023, 1, 1); // Invalid date range
// //         Period period = Period.MONTHLY;
// //         // Act & Assert: Perform request and expect HTTP 400 Bad Request due to @AssertTrue
// //         mockMvc.perform(get(buildSummaryUrl(startDate, endDate, period))
// //                        .contentType(MediaType.APPLICATION_JSON))
// //                .andExpect(status().isBadRequest());
// //                // Again, check response body for specific validation error message if needed
// //     }
//     @Test
//     void getRevenueSummary_EndDateBeforeStartDate_ReturnsBadRequest() throws Exception {
//         // Arrange: Invalid date range (endDate before startDate) - testing @AssertTrue
//         LocalDate startDate = LocalDate.of(2023, 1, 31);
//         LocalDate endDate = LocalDate.of(2023, 1, 1); // Invalid date range
//         Period period = Period.MONTHLY;

//         // Act & Assert: Perform request and expect HTTP 400 Bad Request due to @AssertTrue
//         try {
//             mockMvc.perform(get(buildSummaryUrl(startDate, endDate, period))
//                     .contentType(MediaType.APPLICATION_JSON));
//             // .andExpect(status().isBadRequest()); // Temporarily comment this out
//         } catch (Exception e) {
//             // Catch the exception and print its type
//             System.out.println("===================================================");
//             System.out.println("Caught Exception Type: " + e.getClass().getName());
//             System.out.println("Exception Message: " + e.getMessage());
//             if (e.getCause() != null) {
//                 System.out.println("Cause Exception Type: " + e.getCause().getClass().getName());
//                 System.out.println("Cause Exception Message: " + e.getCause().getMessage());
//             }
//             System.out.println("===================================================");
//             throw e; // Re-throw the exception so the test still fails
//         }

//         // If the try block completes without exception (which is happening now, resulting in 200),
//         // the assertion below will fail, confirming the status issue.
//         mockMvc.perform(get(buildSummaryUrl(startDate, endDate, period))
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isBadRequest()); // Keep the original assertion to confirm the failure
//     }

//     @Test
//     void getRevenueSummary_ServiceReturnsEmptyList_ReturnsOkAndEmptyList() throws Exception {
//         // Arrange: Service returns an empty list
//         LocalDate startDate = LocalDate.of(2023, 1, 1);
//         LocalDate endDate = LocalDate.of(2023, 1, 31);
//         Period period = Period.MONTHLY;

//         List<RevenueSummaryResponse> mockSummaryList = Collections.emptyList();

//         when(revenueReportService.getRevenueSummary(any()))
//                 .thenReturn(mockSummaryList);

//         // Act & Assert: Perform request and expect HTTP 200 OK with an empty JSON array
//         mockMvc.perform(get(buildSummaryUrl(startDate, endDate, period))
//                 .contentType(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$", hasSize(0))); // Expect an empty JSON array
//     }

// }
