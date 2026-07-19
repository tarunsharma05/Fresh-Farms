package com.cg.freshfarmonlinestore.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import com.cg.freshfarmonlinestore.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
	
    private long paymentId;
    @NotBlank(message = "Payment mode cannot be blank")
    
    private String paymentMode;
    @Pattern(regexp = "^(\\d{4}-){3}\\d{4}$", message = "Card number must be in the format XXXX-XXXX-XXXX-XXXX")
    
    private String cardNumber;
    @Size(max = 100, message = "Name on card cannot exceed 100 characters")
    
    private String nameOnCard;
    @Pattern(regexp = "(0[1-9]|1[0-2])\\/\\d{2}", message = "Expiry must be in MM/YY format")
	
    private String expiry;
    @Pattern(regexp = "\\d{3}", message = "CVV must be a 3-digit number")
    
    private String cvv;
	@Pattern(regexp = "[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}", message = "UPI ID must be valid")
	
	private String upiId;
	
	private double totalAmount;
	
	private int itemCount;
    
	private LocalDate paymentDate;
    
	private LocalTime paymentTime;
    
	private String paymentStatus;
    
	private Customer customer;
}