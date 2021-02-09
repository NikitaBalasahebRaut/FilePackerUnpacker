//project packer unpacker
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class Main
{
	public static void main(String args[])
	{
		Scanner sobj = new Scanner(System.in);
        System.out.println("------------------------------");
        System.out.println("packer - unpacker");
		
		while(true)                                                //loop iterate until user not exit from the application
		{
	
	    System.out.println("------------------------------");
		System.out.println("Enter Your choice");
		System.out.println("1 : Packing");
		System.out.println("2 : Unpacking");
		System.out.println("3 : Exit");
	    System.out.println("------------------------------");
		
		int choice = 0;
		String Dir,Filename;
		choice = sobj.nextInt();
		
		switch(choice)
		{
			case 1:
			     System.out.println("Enter Directory name");
				 Dir = sobj.next();
				 
				 System.out.println("Enter file name for packing");
				 Filename = sobj.next();
				 
				 Packer pobj = new Packer(Dir,Filename);              //pass the folder name and file name to the packer constructor
				 break;
				 
				 case 2:
				      System.out.println("Enter Packed File Name \n");
					  String name = sobj.next();
					  UnPacker obj = new UnPacker(name);
				      break;
				case 3:
				    System.out.println("-----------------------------------------");
				    System.out.println("Thank you for using packer unpacker");
					System.out.println("-----------------------------------------");
					System.exit(0);
				    break;
				
			   default:
			         System.out.println("Wrong choice");
					 break;
		}
		}
	}
}
class Packer
{
	//object for file writing
	public FileOutputStream outstream = null;            //reference outstream
	
	//parameterized constructor
	public Packer(String FolderName,String FileName)
	{
		try
		{
			
		//Create new file for packing
		File outfile = new File(FileName);
		outstream = new FileOutputStream(FileName);
		
		//set the current working directory for folder traversal
		//System.setProperty("user.dir",FolderName);
		TravelDirectory(FolderName);
		}
		catch(Exception obj)
		{
			System.out.println(obj);
		}
	}
	
	public void TravelDirectory(String path)
	{
		File directoryPath = new File(path);
		int Counter = 0;
		
		//Get all file name from directory
		File arr[] = directoryPath.listFiles();          //listFiles is the method of File class 
		
		System.out.println("--------------------------");
		for(File filename : arr)
		{
			//System.out.println(filename.getName());//filename.getAbsolutePath());
			if(filename.getName().endsWith(".txt"))
			{
				Counter++;
				System.out.println("Packed file :"+filename.getName());
			    PackFile(filename.getAbsolutePath());
			}
		}
		System.out.println("-------------------------------");
		System.out.println("Succesfully packed files :"+Counter);
		System.out.println("-------------------------------");
	}
	public void PackFile(String FilePath)
	{
		//System.out.println("File name received"+FilePath);
		byte Header[] = new byte[100];                    //array byte data type//one array for Header and head size 100 byte
		byte Buffer[] = new byte[1024];                   //these array for actual file data it size 1024
		int length = 0;
		
		FileInputStream istream = null;            //for reading file
		
		File fobj = null;
		fobj = new File(FilePath);
		
		String temp = FilePath+" "+fobj.length();           //filename space length (Data.txt  15)
		
		//Create  header of 100 bytes
		for(int i = temp.length();i < 100;i++)
		{
			temp = temp + " "; //add space after header  so complite 100 byte of header
		}
		Header = temp.getBytes();         //conver data into byte (because header of type string)
		try                             //exception yeu shakto
		{
			//open the file for reading
			istream = new FileInputStream(FilePath);
			
			outstream.write(Header,0,Header.length);       //for wrting header
			while((length = istream.read(Buffer)) > 0)     //read one by one and then write into newly created file ia allcombine
			{
				outstream.write(Buffer,0,length);         //for wrting data 
			}
			istream.close();                            //close file after writing
		}
		catch(Exception obj)
		{
			
		}
		//System.out.println("Header :"+temp.length());
		//packing logic
	}
}

class UnPacker
{
	public FileOutputStream outstream = null;
	
	public UnPacker(String src)
	{
		UnPackFile(src);
	}
	public void UnPackFile(String FilePath)
	{
		try
		{
		FileInputStream instream = new FileInputStream(FilePath);//for open file 
		byte Header[] = new byte[100];
		int length = 0;
		int Counter = 0;
		
		while((length = instream.read(Header,0,100)) > 0)
		{
			String str = new String(Header);
			
			//c:/asdas/asdas/asdas/demo.txt 45
			String ext = str.substring(str.lastIndexOf("\\"));
			
			ext = ext.substring(1);
			
			String words[] = ext.split("\\s");//   //stukda kadhaych \\s in java indicate it is space
			String name = words[0];
			int size = Integer.parseInt(words[1]);
			
			byte arr[] = new byte[size];
			instream.read(arr,0,size);
			
			System.out.println("New File gets created as:"+name);
			//New file gets created 
			FileOutputStream fout = new FileOutputStream(name);
			//write the data into newly created file
			fout.write(arr,0,size);
			Counter++;
		}
		   System.out.println("Succesfully unpacked files :"+Counter);
		}
        catch(Exception obj)
		{
		}			
			
		}
	
}

/*
output:


D:\Packer_Unpacker>javac Main.java

D:\Packer_Unpacker>java Main
------------------------------
Marvellous packer - unpacker
------------------------------
Enter Your choice
1 : Packing
2 : Unpacking
3 : Exit
------------------------------
1
Enter Directory name
Demo
Enter file name for packing
AllCombine
--------------------------
Packed file :Hello.txt
Packed file :India.txt
Packed file :World.txt
-------------------------------
Succesfully packed files :3
-------------------------------
------------------------------
Enter Your choice
1 : Packing
2 : Unpacking
3 : Exit
------------------------------
2
Enter Packed File Name

AllCombine
New File gets created as:Hello.txt
New File gets created as:India.txt
New File gets created as:World.txt
Succesfully unpacked files :3
------------------------------
Enter Your choice
1 : Packing
2 : Unpacking
3 : Exit
------------------------------
3
-----------------------------------------
Thank you for using packer unpacker
-----------------------------------------

*/
