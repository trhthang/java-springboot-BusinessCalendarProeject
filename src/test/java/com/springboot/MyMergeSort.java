//package com.springboot;
//
//public class MyMergeSort {
//	
//	
//	public int[] merge(int[] a1, int[] a2) {
//		int n = a1.length + a2.length;
//		int[] result = new int[n];
//		
//		
//		// i là của mảng result, i1 của mảng a1, i2 của mảng a2
//		int i = 0, i1 = 0, i2 = 0 ;
//		while(i < n) {
//			if(i1 < a1.length && i2 < a2.length ) { // a1 và a2 khác rỗng
//				 if(a1[i1] <= a2[i2]) {// a1 nhỏ hơn
//					 result[i] = a1[i];
//					 i++; i1++;
//				 }else { // a2 nhỏ hơn
//					 result[i] = a2[i2];
//					 i++; i2++;
//				 }
//			}else { // a1 rỗng hoặc a2 rỗng
//				if(i1 < a1.length){// a1 không rỗng
//					result[i] =a1[i1];
//					i++;i1++;
//				
//				} else { // a2 không rỗng
//					result[i] = a2[i2];
//					i++; i2++;
//				}
//			}
//		}
//		
//		return result;
//	}
//	
//	//Hàm này sử dụng đệ quy
//	public int[] mergeSort(int a[] , int L , int R) { // a là 1 mảng , 
//													//L là index đầu tiên của mảng a
//													// R là index cuối của mảng a
//		
//		
//		//Điều kiện dừng trong đệ quy 
//		if(L > R ) return new int[0];
//		if(L == R) { // trường hợp mảng chỉ còn 1 phần tử
//			int[] singleElement = {a[L]};
//			return singleElement;
//		}
//		
//		
//		// Trường hợp tổng quát
//		
//		//Chia ra
//		System.out.println("Chia: " + L + " - " + R);
//		int k = (L+R)/2;
//		int[] a1 = mergeSort(a, L, k);// nửa đầu của mảng
//		int[] a2 = mergeSort(a, k+1 , R); // nửa sau của mảng
//		
////		Trộn vào : a2 và a1 là các mảng đã được sắp xếp
//
//		
//	} 
//	
//	
//		
//	
//	public int[] sortArray(int[]  nums) {
//		return mergeSort(nums, 0, nums.length -1);
//	}
//	
//	
//	public static void main(String[] args) {
//		
//	}
//}
