package com.qa.api.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductLombok {
	
		
		
		private Integer id;
		private double price;
		private String title;
		private String description;
		private String image;
		private String category;
		private Rating rating;
		
		@Data
		@Builder
		@AllArgsConstructor
		@NoArgsConstructor
		public  static class Rating
		{
			private String rate;
			private String count;
		}
		

	}


