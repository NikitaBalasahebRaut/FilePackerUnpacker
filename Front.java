import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class Front
{
	public static void main(String args[])
	{
		 Window obj = new Window();
                
	}
}
class Window              //implements ActionListener
{
	public Window()
	{
	JFrame f = new JFrame("Packer");
	
	JButton bobj = new JButton("Pack");
	bobj.setBounds(100,200,140,40);
	
	JLabel lobj1 = new JLabel("Enter Directory Name");
	lobj1.setBounds(10,10,100,100);
	
	JTextField tf1 = new JTextField();
	tf1.setBounds(100,50,130,30);
	
	JLabel lobj2 = new JLabel("Enter File name");
	lobj2.setBounds(10,110,100,100);
	
	JTextField tf2 = new JTextField();
	//(x cordinate,y cordinate,with,height)
	tf2.setBounds(10,110,100,30);
	
	f.add(lobj1);
	f.add(bobj);
	f.add(tf1);
	f.add(lobj2);
	f.add(tf2);
	
	f.setSize(300,300);
	f.setLayout(null);
	f.setVisible(true);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

	bobj.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj){
				Packer pobj = new Packer(tf1.getText(), tf2.getText());
				f.setVisible(false);
				NewWindow o = new NewWindow();
			}
		});
	}
}

class NewWindow 
{
	public NewWindow()
	{
		JFrame fobj = new JFrame("UnPacker");

                JButton b1 = new JButton("Extract Here");
	        b1.setBounds(100,200,140,40);

                JLabel lab1 = new JLabel("Enter PackedFile name");
                lab1.setBounds(10,10,100,100);

                JTextField text1 = new JTextField();
	        text1.setBounds(100,50,130,30);

                fobj.add(lab1);
                fobj.add(b1);
                fobj.add(text1);

		fobj.setSize(300,300);
                fobj.setLayout(null);
		fobj.setVisible(true);
		fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

               	b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent eobj2){
			  UnPacker uobj = new UnPacker(text1.getText());
				fobj.setVisible(false);
				
			}
		});

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



/*output
D:\Packer_Unpacker>javac Front.java

D:\Packer_Unpacker>java Front
--------------------------
Packed file :Day.txt
Packed file :Happy.txt
Packed file :Republic.txt
-------------------------------
Succesfully packed files :3
-------------------------------
New File gets created as:Day.txt
New File gets created as:Happy.txt
New File gets created as:Republic.txt
Succesfully unpacked files :3

*/