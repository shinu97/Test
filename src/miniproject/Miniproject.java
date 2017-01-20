/* ************************************************************************************************************
Author Shinu
In this program a alien pet is created. A hunger, thirst and irritability rate is generated randomly. an 
anger rate is then calculated that describes its emotion is  accordingly. 4 rounds are played for the created 
alien pet where in each round you can choose whether to feed water, feed food, sing or skip the rounds.
After the rounds have been played, the alien pet will create babies and flee. You can name and choose how many 
alienpets you want to create. the alien babies each have a hunger, thirst and irritability rate and a comment 
will be printed depending on the number of the anger rate calculated. You can play rounds with an alien baby 
but cannot chose to play with the same baby again. The babies are then sorted in ascending order of the anger
rate and printed. The anger rate and name of the babies are saved into a text file called savestate.txt. When 
the program is exited and then opened again a welcome back message along with the baby information if the program
has been run before.
***************************************************************************************************************/
import java.util.Random;
import javax.swing.*;
import java.io.*;
class Miniproject
{
	public static void main (String[] param) throws IOException
	{
		BufferedReader inStream = new BufferedReader(new FileReader("savestate.txt")); // Created a Reader object connected to the file
        String nextword = inStream.readLine(); // Read a single line in from the file and print it to the screen
		if(nextword == null) //if there is already text in the file then skip the methods below
		{
			final int maxrate= 10; //a literal value that the max number a rate can be
			String name = namepet("what is the name of your pet?");
			int hungerrate = hunger(maxrate);
			int thirstrate = thirst(maxrate);
			int irritabilityrate = irritability(maxrate);
			emotionalstate(hungerrate, thirstrate, irritabilityrate, name, maxrate);
			rounds(hungerrate, thirstrate, irritabilityrate, name, maxrate);
			int amount = alienbabyamount(name);
			String[] namearray = namealienbaby(amount, name);
		
			int [] hungers = new int[amount]; //arrays are created that store hunger, thirst, irritability and anger rate for each baby alien
			int [] thirsts = new int[amount];
			int [] irritabilities = new int[amount];
			int [] angers = new int[amount];
			for( int i = 0; i<hungers.length ; i++) //this for loop fills in the babies' emotion arrays and then the arrays are passed on to the emotionalstate method
			{
				String CurrentAlienName = namearray[i];
				hungers[i] = hunger(maxrate);
				thirsts[i] = thirst(maxrate);
				irritabilities[i] = irritability(maxrate);
				angers[i]=(hungers[i]+thirsts[i]+irritabilities[i])/3;
				emotionalstate(hungers[i], thirsts[i], irritabilities[i], CurrentAlienName, maxrate);
			}
			
			String question = JOptionPane.showInputDialog("Do you want to play rounds with an alien baby? (yes or no)"); //input to decided whether or not to interact with an alien baby
			if(question.equals("yes")) //if no is typed then the baby interaction is skipped
			{
				String searchbaby = JOptionPane.showInputDialog("Which baby do you want to play rounds with?");
				while(question.equals("yes"))
				{
					
					int result = 0;

					for (int k=0; k<namearray.length; k++) //for loop that searches for the baby name that was inputted
					{
						if (namearray[k].equals(searchbaby))
						{
							result = k;   
						}
					}
					angers[result] = rounds(hungers[result], thirsts[result], irritabilities[result], namearray[result], maxrate); //the chosen baby's information is passed on the rounds method
					
					question = JOptionPane.showInputDialog("Do you want to play rounds with another alien baby? (yes or no)");
					if(question.equals("no")) //if no is typed, the loop will break
					{
						break;
					}
					searchbaby = JOptionPane.showInputDialog("Which baby do you want to play rounds with?");
					
					while(searchbaby.equals(namearray[result])) //if the baby picked previously is picked a again then a sorry message will be printed until a different baby is chosen
					{
						JOptionPane.showMessageDialog(null, "Sorry. "+searchbaby+" doesn't want to play with you again. please chose another alien baby.");
						searchbaby = JOptionPane.showInputDialog("Which baby do you want to play rounds with?");
					}
				}
			}
		    sortingoutput(angers, namearray);
			System.out.println("alien babies have been saved");
		}
		else //a welcome message showing that the program has already been executed before and the babies' information is printed
		{
			JOptionPane.showMessageDialog(null, "welcome back! Your alien babies' angers will be displayed in the console");
			System.out.println("alien babies have been loaded...");
			while (nextword != null)
			{
				System.out.println(nextword);
				nextword = inStream.readLine();
            
			}
			inStream.close();
		}
		System.exit(0);
	} //end main

	public static String namepet(String text) //a variable for input name is created and a happy birthday message is printed. the name is then returned
	{
		String name = JOptionPane.showInputDialog(text);
		JOptionPane.showMessageDialog(null, "Happy 0th Birthday "+name+" the Alien!");
		return name; 
	}
	
	public static int hunger(int maxrate) //a random number is generated for hunger and then returned
    {
        Random rate = new Random(); 
		int ratestored = rate.nextInt(maxrate) +1;
		return ratestored;
	}
	
	public static int thirst(int maxrate) //a random number is generated for thirst and then returned
    {
        Random rate = new Random();
		int ratestored = rate.nextInt(maxrate) +1;
		return ratestored;
	}
	
	public static int irritability(int maxrate) //a random number is generated for irritability and then returned
    {
		Random rate = new Random();
		int ratestored = rate.nextInt(maxrate) +1;
		return ratestored;
	}
	

	public static int emotionalstate(int hungerrate, int thirstrate, int irritabilityrate, String name, int maxrate)
	{
		JOptionPane.showMessageDialog(null, name+"'s hunger rate is " +hungerrate+ "/"+maxrate); //all rates are being printed
		JOptionPane.showMessageDialog(null, name+"'s thirst rate is " +thirstrate+ "/"+maxrate);
		JOptionPane.showMessageDialog(null, name+"'s irritability rate is " +irritabilityrate+ "/"+maxrate);
		JOptionPane.showMessageDialog(null, name+"'s anger rate is being calculated...");
		int angerrate = (hungerrate+thirstrate+irritabilityrate)/3; //anger rate is calculated by taking the average of the hunger, thirst and irritability rate
		JOptionPane.showMessageDialog(null, "...anger rate is "+angerrate);
		if(angerrate<=10 && angerrate>=8) //depending on the number, a comment is printed
		{
			JOptionPane.showMessageDialog(null, name+" is looking dangerous!");
		}
		else if(angerrate<=7 && angerrate>=4)
		{
			JOptionPane.showMessageDialog(null, name+" seems to be quite tetchy.");
		}
		else if(angerrate<=3 && angerrate>=1)
		{
			JOptionPane.showMessageDialog(null, name+" seems to be quite calm.");
		}
		return angerrate;
	}
	
	public static int rounds(int hungerrate, int thirstrate, int irritabilityrate, String name, int maxrate) //in this method an input box pops up which lets you interact with the pet
	{
		String response="";
		int newhunger=hungerrate;
		int newthirst=thirstrate;
		int newirritability=irritabilityrate;
		boolean timecontrol = true;
		JOptionPane.showMessageDialog(null, "4 rounds will be played with "+name);
		while(timecontrol) //while loop will only break if skip is typed or i equals 4 in the for loop
		{
			
			for(int i=1; i<=4; i++)
			{
				JOptionPane.showMessageDialog(null, "Some time has passed. Type SKIP to end the rounds.\n round:"+i);
				JOptionPane.showMessageDialog(null, name+"'s stats: \n hunger rate now:"+newhunger+"\n thirst rate now:"+newthirst+"\n irritability rate now:"+newirritability);
				response = JOptionPane.showInputDialog("Would you like to:sing or feed:water or feed:food to "+name+"?");
				if(i==4) //when i is 4 the loops will break
				{
					timecontrol = false;
					break;
				}
				else if(response.equals("food")) //if food ,water or sing is typed the rates will change accordingly
				{
					JOptionPane.showMessageDialog(null, "You feed food to "+name+" \n Its hunger rating has been lowered.");
					newhunger = newhunger - 1; 
					JOptionPane.showMessageDialog(null, "Its hunger rating is now lowered to "+newhunger);
				}
				else if(response.equals("water")) 
				{
					JOptionPane.showMessageDialog(null, "You feed water to "+name+" \n Its thirst rating has been lowered.");
					newthirst = newthirst - 1; 
					JOptionPane.showMessageDialog(null, "Its thirst rating is now lowered to "+newthirst);
				}
				else if(response.equals("sing"))
				{
					JOptionPane.showMessageDialog(null, "You sing to "+name+" \n Its irritability rating has been lowered.");
					newirritability = newirritability - 1; 
					JOptionPane.showMessageDialog(null, "Its irritability rating is now lowered to "+newirritability);
				}
				else if(response.equals("skip")||response.equals("SKIP")) //if skip is typed then rounds will end
				{
					timecontrol = false;
					break;
				}
				else //if an invalid response is typed then all the rates will increase.
				{
					newhunger = newhunger + 1; 
					newthirst = newthirst + 1; 
					newirritability = newirritability + 1;
					JOptionPane.showMessageDialog(null, name+"'s hunger, thirst and irritability ratings have all increased!");
					if(newhunger>maxrate) //if a rate is higher than 10 then it will remain 10 and the value will not change
					{
						newhunger=maxrate;
					}
					if(newthirst>maxrate)
					{
						newthirst=maxrate;
					}
					if(newirritability>maxrate)
					{
						newirritability=maxrate;
					}
				}
			}
		}
		int anger=(newhunger+newthirst+newirritability)/3;
		return anger; //anger rate is calculated and returned
	} // end rounds
	
	public static int alienbabyamount(String name) //an input asks for the number of babies to create. the nubmer is then returned
	{
		JOptionPane.showMessageDialog(null, name+" is now creating babies!");
		String input = JOptionPane.showInputDialog("How many babies should "+name+" create?");
		int number = Integer.parseInt(input);
		return number; // end alienbabyamount
	}
	
	public static String[] namealienbaby(int amount, String name) //this method asks what the alien babies should be called. the amount of babies created is dependant on the previous input
	{
		String [] AlienNames = new String[amount]; //an array to store alien names is created
		
		for(int z =0; z<AlienNames.length; z++)
		{
			int number = z+1;
			String babyname = JOptionPane.showInputDialog("Baby number "+number+" has been born. what would you like to name it?");
			AlienNames[z] = babyname; //baby name is stored in an array
		}
		JOptionPane.showMessageDialog(null, name+" has trusted you with the alien babies and flies away.\n The alien babies' fate is in your hands! ");
		return AlienNames;	
	} //end namealienbaby
	
	public static void sortingoutput(int [] angers, String[] namearray) throws IOException
	{
		JOptionPane.showMessageDialog(null, "A list will be displayed in the console showing your alien babies in ascending order based on anger rate!");
		int swapnumber = 0;
		String swapname="";
		for(int i=0; i<angers.length; i++) //for loop for each pass
		{
			for(int j=0; j<angers.length-1; j++) //for loop for sorting in bubble sort
			{
				if(angers[j]>angers[j+1])
				{
					swapnumber=angers[j]; // anger is sorted in ascending order
					angers[j]=angers[j+1];
					angers[j+1]=swapnumber;
					
					swapname=namearray[j]; //name is sorted based on anger
					namearray[j]=namearray[j+1];
					namearray[j+1]=swapname;
				}
			}
		}
		PrintWriter outputStream = new PrintWriter(new FileWriter("savestate.txt"));  //create a stream object so you can access the savestate.txt file
		for (int c=0; c<angers.length; c++) 
		{
			 System.out.println(namearray[c]+"'s anger rate is:"+angers[c]); //anger rate and baby name is printed in console
			 outputStream.println(namearray[c]+"'s anger rate is:"+angers[c]); // the baby name and anger rate is printed to savestate.txt
		}
        outputStream.close(); 
	} // end sortingoutput
} //end class