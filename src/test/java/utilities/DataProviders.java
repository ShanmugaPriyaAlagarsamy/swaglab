package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginDataWithAcceptedUsernameAndPassword")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\SwagLabs_LoginDataWithAcceptedUsernamesAndPassword.xlsx";//taking xl file for testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];
		for(int i=1;i<=totalrows;i++)  
		{		
			for(int j=0;j<totalcols;j++)  
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  
			}
		}
	return logindata;
				
	}
	
	@DataProvider(name="LoginDataWithValidUsernameAndInvalidPassword")
	public String [][] getData2() throws IOException
	{
		String path=".\\testData\\SwagLabs_LoginDataWithValidUsernameAndInvalidPassword.xlsx";//taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];
		for(int i=1;i<=totalrows;i++)  
		{		
			for(int j=0;j<totalcols;j++)  
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  
			}
		}
	return logindata;
				
	}
	
	
	@DataProvider(name="LoginDataWithInvalidUsernameAndValidPassword")
	public String [][] getData3() throws IOException
	{
		String path=".\\testData\\SwagLabs_LoginDataWithInvalidUsernameAndValidPassword.xlsx";//taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];
		for(int i=1;i<=totalrows;i++)  
		{		
			for(int j=0;j<totalcols;j++)  
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  
			}
		}
	return logindata;
				
	}
	
	
	@DataProvider(name="LoginDataWithInvalidUsernameAndInvalidPassword")
	public String [][] getData4() throws IOException
	{
		String path=".\\testData\\SwagLabs_LoginDataWithInvalidUsernameAndInvalidPassword.xlsx";//taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);//creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("Sheet1");	
		int totalcols=xlutil.getCellCount("Sheet1",1);
				
		String logindata[][]=new String[totalrows][totalcols];
		for(int i=1;i<=totalrows;i++)  
		{		
			for(int j=0;j<totalcols;j++)  
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1",i, j);  
			}
		}
	return logindata;
				
	}
	
	@DataProvider(name="LoginDataWithBlankUsernameAndValidPassword")
	public String [][] getData5() throws IOException
	{
		String[][] logindata = {{"","secret_sauce"}};
				
	return logindata;
				
	}

	@DataProvider(name="LoginData")
	public String [][] getData6() throws IOException
	{
		String[][] logindata = {{"standard_user","secret_sauce"},{"problem_user","secret_sauce"}};
				
	return logindata;
				
	}
	
	@DataProvider(name="ImageTestUsers")
	public String [][] getData7() throws IOException
	{
		String[][] logindata = {{"standard_user","secret_sauce"},{"locked_out_user","secret_sauce"},{"problem_user","secret_sauce"}};
				
	return logindata;
				
	}
	
}
