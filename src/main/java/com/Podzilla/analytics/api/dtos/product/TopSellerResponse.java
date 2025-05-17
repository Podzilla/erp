package com.Podzilla.analytics.api.dtos.product;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
// @Builder
public class TopSellerResponse {
    @Schema(
        description = "Product ID",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private UUID productId;
    @Schema(description = "Product name", example = "Wireless Mouse")
    private String productName;
    @Schema(description = "Product category", example = "Electronics")
    private String category;
    @Schema(description = "Total value sold", example = "2500.75")
    private BigDecimal value;

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private UUID productId;
        private String productName;
        private String category;
        private BigDecimal value;

        public Builder productId(final UUID productId) {
            this.productId = productId;
            return this;
        }

        public Builder productName(final String productName) {
            this.productName = productName;
            return this;
        }

        public Builder category(final String category) {
            this.category = category;
            return this;
        }

        public Builder value(final BigDecimal value) {
            this.value = value;
            return this;
        }

        public TopSellerResponse build() {
            return new TopSellerResponse(
                productId,
                productName,
                category,
                value
            );
        }
    }

}
