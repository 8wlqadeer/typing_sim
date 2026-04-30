package Part2;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class TypingRaceGUI extends JFrame
{
  private JList<String> list; //used to store passage options and selected passage
  private JTextArea textArea; //used to display passage and input custom passage
  private boolean autocorrectEnabled=false; //difficulty modifier that halves slide back amount
  private boolean caffeineEnabled = false;//difficulty modifier that gives a speed boost for the first 10 turns
  private boolean nightshiftEnabled=false;//difficulty modifier that reduces accuracy across the board
  private int minPlayers=2;//default number of players, can be changed in settings
  private String selectedPassage; //the actual passage
  private String customText="";   //stores custom passage input so that it can be re displayed if user goes back to passage selection
  private boolean selected =false;//used to check if passage selected 
  private int selectedPassageIndex=-1; //saves highlighted item
  private boolean[] symbolTaken=new boolean[6];//check for symbols taken so that they can be disabled in the options
  private boolean[] ColourTaken= new boolean[6];//check for colour taken
  private String[] typistSymbols=new String[6];//holds all symbols
  private boolean[] wristSupport=new boolean[6];//holds wrist support status for each player
  private boolean[]energyDrink= new boolean[6];//holds energy drink status for each player
  private Color[] typistColours= new Color[6];//hold colour of each player
  private double[] typistAccuracies= new double[6]; //hold accuracies of each player, used to save customisation choices
  private int[] selectedColourIndex = new int[6];//hold selected colour index for each player so that it can be re highlighted if user goes back to customisation
 private ArrayList<Integer>burnouts= new ArrayList<>();//used to track burnouts for stats 
  private ArrayList<Integer>mistypes= new ArrayList<>();//used to track mistypes for stats
   private ArrayList<Integer>correctOnes= new ArrayList<>();//used to track correct keystrokes for stats
 private ArrayList<Double>pbs= new ArrayList<>();//used to track pbs for stats and leaderboard
  private long start; //used to track time for stats and leaderboard
  private double[] earnings= new double[6]; //used to track earnings for sponsors board
  private int[] winsInaRow= new int[6];//used to track wins in a row for sponsors board
  private int[] noBurnoutStreak= new int[6];//used to check which players have 
  private String[] sponsors={"KeyCorp","TypeMaster","SpeedKeys","FingerFlex","KeyBlaze","RapidType"};//used to assign sponsors based on earnings


  
    public static void main(String[] args)
    {
 
       TypingRaceGUI app = new TypingRaceGUI();
       app.HomePage();

    }
    public void HowToPlay() //displays instructions on how to play the game and goes back to home page when back button is pressed
    {
      JFrame frame= new JFrame("Typing Race");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600,400);
      frame.setLayout(new BorderLayout());
      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "How To Play");
       frame.add(TitlePanel, BorderLayout.NORTH);

       JTextArea instructions = new JTextArea("Welcome to Typing Race Simulator .The simulator contains a home page which has options  :START, HOW TO PLAY, SET PLAYERS,MODIFY DIFFICULTY. you should set players (between 2-6), default is 2, before pressing start. You should also add any modifiers by clicking on MODIFY DIFFICULTY: these are Autocorrect (halves slideback amount), Caffeine Mode (first 10 turns get a speed boost) and Night Shift (everyone is tired so accuracy is reduced). After pressing start, you can select a passage to type, either short, medium, long or custom. If you select custom, you can input your own passage in the text box. After selecting passage, you can customise your typist: choose between touch typist (fast and accurate) or hunt and peck (slow but steady), choose a symbol and colour to represent you in the race, choose a keyboard (mechanical for a small accuracy boost or touch screen for a small accuracy decrease) and choose accessories (wrist support for shorter burnout duration and energy drink for a temporary speed boost). After customisation, the race will start abd the passage is displayed above and under will show the players racing across the screen through their progress bars. After a player has won the STATS are displayed and you can then choose to check the leaderboard or the sponsors board. The sponsors board is for typists who have a zero burnout streak and earnings are given to typists based on how well they perform through races, so the more you win and the less you burnout, the more money you earn and the higher you will be on the sponsors board. The leaderboard is based on PB time, so the typist with the fastest PB time will be at the top of the leaderboard. You can then choose to go back to the home page and change settings or start a new simulation");
       instructions.setEditable(false);
        instructions.setLineWrap(true);//goes down in space not just in a lines
        instructions.setWrapStyleWord(true);//wraps at word not just character
       frame.add(instructions, BorderLayout.CENTER);
       frame.setVisible(true);
       JButton back = new JButton("BACK");
       back.setBackground(Color.decode("#f5d9ea"));
      back.setForeground(Color.decode("#AF7F73"));
      back.setFont(new Font("Verdana", Font.ITALIC, 18));
        back.addActionListener(e -> {
            frame.dispose();
            HomePage();
        }); 
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setPreferredSize(new Dimension(500,25));
        bottom.setBackground(Color.decode("#FADDE1"));
        bottom.add(back, BorderLayout.WEST);
        frame.add(bottom, BorderLayout.SOUTH);
        
    }
    public void HomePage() //displays home page with options to start game, see how to play, set players and modify difficulty along with background that i made in aesprite
    {
           JFrame frame= new JFrame("Typing Race");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600,400);
      frame.setLayout(new BorderLayout());

      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "Set Number Of Players");

      

      JPanel leftPanel= new JPanel();
      leftPanel.setPreferredSize(new Dimension(200,400));//1/3 of frame
      leftPanel.setBackground(Color.decode("#AF7F73"));
      JPanel gridPanel= new JPanel(new GridLayout(4,1)); 
      JButton startButton= new JButton("START");
      startButton.setBackground(Color.decode("#FADDE1"));
      startButton.setForeground(Color.decode("#AF7F73"));
      startButton.setFont(new Font("Verdana", Font.ITALIC, 30));

      startButton.addActionListener(e -> {
           frame.dispose();
           GamePage();

      });

      JButton infoButton = new JButton( "HOW TO PLAY");
      infoButton.setBackground(Color.decode("#FADDE1"));
      infoButton.setForeground(Color.decode("#AF7F73"));
      infoButton.setFont(new Font("Verdana", Font.ITALIC, 20));
      infoButton.addActionListener(e -> {
           frame.dispose();
           HowToPlay();

      });

      JButton setPlayers = new JButton( "SET PLAYERS");
      setPlayers.setBackground(Color.decode("#FADDE1"));
      setPlayers.setForeground(Color.decode("#AF7F73"));
      setPlayers.setFont(new Font("Verdana", Font.ITALIC, 20));

      setPlayers.addActionListener(e ->{
        frame.dispose();
        SetPlayers();
      });

      JButton difficultyModifier = new JButton( "MODIFY DIFFICULTY");
      difficultyModifier.setBackground(Color.decode("#FADDE1"));
      difficultyModifier.setForeground(Color.decode("#AF7F73"));
      difficultyModifier.setFont(new Font("Verdana", Font.ITALIC, 18));

      difficultyModifier.addActionListener(e ->{
        frame.dispose();
        ModifyDifficulty();

      });

      gridPanel.add(startButton);
      gridPanel.add(infoButton);
      gridPanel.add(setPlayers);
      gridPanel.add(difficultyModifier);
       leftPanel.add(gridPanel);

       JPanel rightPanel = new JPanel(new BorderLayout());
       JLabel imageLabel= imageException("Part2/MainImage.png"); 
       rightPanel.add(imageLabel, BorderLayout.CENTER);
       rightPanel.setBackground(Color.decode("#AF7F73"));
      frame.add(leftPanel, BorderLayout.WEST);
      frame.add(rightPanel, BorderLayout.CENTER);
      frame.setVisible(true);
    }

    public static JLabel imageException(String path) //used to catch exception if image doesnt load properly e.g. if i reference loaction wrong
    {
      
        {
           try 
           {
               ImageIcon icon = new ImageIcon(path);
              if(icon.getIconWidth()==-1) //check if icon loads
             {
              throw new Exception("Image not foun at" + path);
             }

             return new JLabel(icon);
           } 
           catch (Exception e) 
           {
                System.out.println("Image load error" + e.getMessage());
                System.out.println(new java.io.File(path).getAbsolutePath());
                 return new JLabel("failed image");
                
           }


           finally
           {
            System.out.println("Attempt to load finished");
           }
         

        }
    }

    public void ModifyDifficulty() //displays options to modify difficulty and goes back to home page when back button is pressed
    {
      JFrame frame= new JFrame("Typing Race");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600,400);
      frame.setLayout(new BorderLayout());

      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "Modify Difficulty");
       frame.add(TitlePanel, BorderLayout.NORTH);

       JPanel bottom= new JPanel();
       BackToHome(frame, bottom);

       JPanel mainPanel = new JPanel(new GridLayout(1,3));
       mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       JPanel col1=new JPanel();
       JPanel col2= new JPanel();
       JPanel col3= new JPanel();

       JCheckBox enable1= new JCheckBox("Enable");
       enable1.setSelected(autocorrectEnabled);
       JCheckBox enable2= new JCheckBox("Enable");
       enable2.setSelected(caffeineEnabled);
       JCheckBox enable3= new JCheckBox("Enable");
       enable3.setSelected(nightshiftEnabled);

       enable1.addActionListener(e -> autocorrectEnabled=enable1.isSelected());
       enable2.addActionListener(e -> caffeineEnabled=enable2.isSelected());
       enable3.addActionListener(e -> nightshiftEnabled= enable3.isSelected());

       col1.setLayout(new GridLayout(3,1));
       col1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       col2.setLayout(new GridLayout(3,1));
       col2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       col3.setLayout(new GridLayout(3,1));
       col3.setBorder(BorderFactory.createLineBorder(Color.BLACK));

       col1.add(new JLabel("Autocorrect"));
       JTextArea text1 = new JTextArea("When enabled, SlideBack amount is halved");
        text1.setLineWrap(true);
       text1.setWrapStyleWord(true);
       text1.setEditable(false);
       text1.setOpaque(false);
       col1.add(text1);
       col1.add(enable1);
       
       col2.add(new JLabel("Caffeine Mode"));
       JTextArea text2=new JTextArea("All typists gain a temporary speed boost for the first 10 turns");
       text2.setLineWrap(true);
       text2.setWrapStyleWord(true);
       text2.setEditable(false);
       text2.setOpaque(false);
       col2.add(text2);
       col2.add(enable2);

       col3.add(new JLabel("Night Shift"));
       JTextArea text3 =new JTextArea("Accuracy ratings are slightly reduced across the board: everyone is tired");
      text3.setLineWrap(true);
       text3.setWrapStyleWord(true);
       text3.setEditable(false);
       text3.setOpaque(false);
       col3.add(text3);
       col3.add(enable3);

       mainPanel.add(col1);
       mainPanel.add(col2);
       mainPanel.add(col3);



       frame.add(mainPanel, BorderLayout.CENTER);

       frame.setVisible(true);

     


    }

    public void GamePage()//shows passage selection and customisation options, then starts the race
    {
          JFrame frame= new JFrame("Typing Race");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600,400);
      frame.setLayout(new BorderLayout());

      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "START GAME");

       JPanel bottom= new JPanel();
       BackToHome(frame, bottom);
      JPanel mainPanel = new JPanel();

      SelectPassage(mainPanel,frame);
      frame.add(TitlePanel, BorderLayout.NORTH);
      frame.add(mainPanel); 

      frame.setVisible(true);
       
    }

    public void SetPlayers()
    {
      JFrame frame= new JFrame("Typing Race");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600,400);
      frame.setLayout(new BorderLayout());

      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "Set Number Of Players");

       JPanel bottom= new JPanel();
       BackToHome(frame, bottom);

      JLabel desc= new JLabel("set number of Players 2-6", SwingConstants.CENTER);
      desc.setFont( new Font("Verdana", Font.ITALIC,14));
      desc.setForeground(Color.decode("#AF7F73"));
      desc.setAlignmentX(Component.CENTER_ALIGNMENT); //center in the  middle horizontally  

      JSlider slider = new JSlider(2,6,minPlayers); //starts at 2
      slider.setMajorTickSpacing(1); //marks every 1
      slider.setPaintTicks(true); //shows little lines
      slider.setPaintLabels(true);//shows numbers
      slider.setAlignmentX(Component.CENTER_ALIGNMENT);
      slider.addChangeListener(e -> minPlayers= slider.getValue()); //makes value set by player stay.

      JPanel card = new JPanel();
      card.setLayout(new BorderLayout());
      card.setBackground(Color.WHITE);
      card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));//even padding


      JPanel centerPanel= new JPanel();
      centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
      centerPanel.setBackground(Color.decode("#FFF0F5"));
      centerPanel.add(Box.createVerticalGlue());//expands to push things into the middle
      centerPanel.add(desc);
      centerPanel.add(Box.createVerticalStrut(20)); //create a fixed gap 
      centerPanel.add(slider);
      centerPanel.add(Box.createVerticalGlue());

      card.add(centerPanel,BorderLayout.CENTER);

   

      frame.add(TitlePanel, BorderLayout.NORTH);
      frame.add(card);
      frame.setVisible(true);
    }
    public void SelectPassage(JPanel p, JFrame frame)
    {
      JPanel bottom = new JPanel();
      BackToHome(frame, bottom);

      String[] passages ={"Short Passage" , "Medium Passage", "Long Passage", "Custom Passage"};

       list = new JList<>(passages);
       list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//only nsingle selection

       textArea= new JTextArea();
       textArea.setEditable(false);
              
        JButton Next = new JButton("NEXT");
      Next.setBackground(Color.decode("#f5d9ea"));
      Next.setForeground(Color.decode("#AF7F73"));
      Next.setFont(new Font("Verdana", Font.ITALIC, 18));
      Next.setEnabled(false);//greyed out until selected
      Next.addActionListener(e -> {
           frame.dispose();
           CustomiseTypists(1);

    });

    
    bottom.add(Next,BorderLayout.EAST);
    bottom.revalidate();// re draw bottom panel
    bottom.repaint(); 


       list.addListSelectionListener(e->
        {
          if(!e.getValueIsAdjusting())
          {
            String picked =list.getSelectedValue();//option clicked
            selectedPassageIndex=list.getSelectedIndex();

             if(picked.equals("Custom Passage"))
              {
                textArea.setText(customText);
                textArea.setEditable(true);
                selectedPassage=customText;
                selected=true;
                Next.setEnabled(true);


              } 
              
              else
              {
                textArea.setEditable(false);
                textArea.setText("The passage you have selected " + selectedPassage);


                switch(picked)
                {
                  case "Short Passage":
                    textArea.setText("There were a couple of birds in a nest. One bird was a parrot, with beautiful blue and red feathers. Another bird was a toucan, a unique bird with a large beautiful beak which had red and blue rims around its beak.");
                    selected=true;
                    break;

                    case("Medium Passage"):

                    textArea.setText("There were a couple of birds in a nest . One bird was a parrot, with beautiful blue and red feathers. Another bird was a toucan, a unique bird with a large beautiful beak which had red and blue rims around its beak. The third bird was a crow, with shiny black feathers. The last bird was a canary, with bright yellow feathers and a beautiful singing voice.");
                    selected=true;
                    break;

                    case("Long Passage"):
                    textArea.setText("There were a couple of birds in a nest , there were four birds living in this nest. One bird was a parrot, with beautiful blue and red feathers. Another bird was a toucan, a unique bird with a large beautiful beak which had red and blue rims around its beak. The third bird was a crow, with shiny black feathers. The last bird was a canary, with bright yellow feathers and a beautiful singing voice. It must be a very odd idea to have a parrot,toucan,crow and canary living together, but the truth was there was a large infestation of these huge bug-looking creatures that were out hunting for the animals in the deep forest and due to some extreme migration, four birds of very different species ended up together.");
                                                          
                    selected=true;
                    break;

                    case("Custom Passage"):
                    selected=true;
                    break;

                    default:
                      textArea.setText("Please select Passage");
                      break;

                }

                selectedPassage= textArea.getText();
                Next.setEnabled(true);


              }
            }
        }
       );


       textArea.addFocusListener(new java.awt.event.FocusAdapter(){ //enables saving and re displaying of custom passage if user clicks away and comes back to it
              public void focusLost(java.awt.event.FocusEvent e){
                if(list.getSelectedValue()!=null && list.getSelectedValue().equals("Custom Passage")) // checks if custom passage is selected
                {
                  customText=textArea.getText();
                  selectedPassage=customText;
                }
              }
       });

       if(selectedPassageIndex!=-1) // checks if user has already selected a passage and if so, re highlights it and displays it in the text area
       {
        list.setSelectedIndex(selectedPassageIndex);//re highlight previous choice
       }

       p.setLayout(new BorderLayout());
       p.add(new JScrollPane(list),BorderLayout.WEST);
       p.add(new JScrollPane(textArea),BorderLayout.CENTER);


       }

     
  

  public JPanel BackToHome(JFrame f, JPanel bottom) //creates a back button that goes back to the home page and adds it to the given panel, used in multiple pages so made it into a method
  {
      bottom.setLayout(new BorderLayout());
      bottom.setPreferredSize(new Dimension(500,25));
      bottom.setBackground(Color.decode("#FADDE1"));
     JButton backButton= new JButton("BACK");
      backButton.setBackground(Color.decode("#f5d9ea"));
      backButton.setForeground(Color.decode("#AF7F73"));
      backButton.setFont(new Font("Verdana", Font.ITALIC, 18));
      bottom.add(backButton,BorderLayout.WEST);
      backButton.addActionListener(e -> {
           f.dispose();
           HomePage();

    });

    f.add(bottom, BorderLayout.SOUTH);

    return bottom;
  }


  
  
  public JPanel Title(JPanel TitlePanel, String title) //creates a title with given text and adds it to the given panel, used in multiple pages so made it into a method
  {
       TitlePanel.setLayout(new BorderLayout());
       TitlePanel.setPreferredSize(new Dimension(500,50));
      TitlePanel.setBackground(Color.decode("#FADDE1")); //change to an image maybe
      JLabel Title= new JLabel(title, SwingConstants.CENTER);
      Title.setFont(new Font("Verdana",Font.ITALIC, 24));
      Title.setForeground(Color.decode("#AF7F73"));
       TitlePanel.add(Title,BorderLayout.CENTER);

       return TitlePanel;
  }

  public void CustomiseTypists(int PlayerNum) //displays customisation options for typists and goes to game page when next button is pressed
  {
   
      JFrame frame= new JFrame("Typing Race");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(900,400);//scales with player size
      frame.setLayout(new BorderLayout());



      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "Customise Typists");
       frame.add(TitlePanel, BorderLayout.NORTH);

       JPanel bottom= new JPanel();
       BackToHome(frame, bottom);

       JPanel mainPanel = new JPanel(new GridLayout(1,5));
       mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

       JPanel StyleCol= new JPanel();
       StyleCol.setLayout(new BoxLayout(StyleCol,BoxLayout.Y_AXIS));
       StyleCol.setBorder(BorderFactory.createLineBorder(Color.black));
       StyleCol.setBackground(Color.decode("#FFF0F5"));

       JLabel nameTitle= new JLabel("NAME");
       nameTitle.setFont(new Font("Verdana", Font.BOLD,13));
       nameTitle.setForeground(Color.decode("#AF7F73"));
       nameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

       JRadioButton touchTypist= new JRadioButton("Touch Typist");
       touchTypist.setBackground(Color.decode("#FFF0F5"));
        touchTypist.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea touchDesc= new JTextArea("Fast and Accurate \nBase accuracy:0.85");
        touchDesc.setEditable(false);
        touchDesc.setOpaque(false);
        touchDesc.setLineWrap(true);
        touchDesc.setWrapStyleWord(true);
        touchDesc.setFont(new Font("Verdana", Font.ITALIC,10));
        touchDesc.setForeground(Color.decode("#AF7F73"));
        touchDesc.setMaximumSize(new Dimension(130,40));

      JRadioButton HuntAndPeck= new JRadioButton("Hunt and Peck");
       touchTypist.setBackground(Color.decode("#FFF0F5"));
        touchTypist.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea HuntAndPeckDesc= new JTextArea("Slow but Steady \nBase accuracy:0.55");
        HuntAndPeckDesc.setEditable(false);
        HuntAndPeckDesc.setOpaque(false);
        HuntAndPeckDesc.setLineWrap(true);
        HuntAndPeckDesc.setWrapStyleWord(true);
       HuntAndPeckDesc.setFont(new Font("Verdana", Font.ITALIC,10));
        HuntAndPeckDesc.setForeground(Color.decode("#AF7F73"));
        HuntAndPeckDesc.setMaximumSize(new Dimension(130,40));

        ButtonGroup styleGroup= new ButtonGroup();
        styleGroup.add(touchTypist);
        styleGroup.add(HuntAndPeck);

        touchTypist.setSelected(true);
        StyleCol.add(Box.createVerticalStrut(10));
        StyleCol.add(nameTitle);
        StyleCol.add(Box.createVerticalStrut(10));
        StyleCol.add(touchTypist);
        StyleCol.add(touchDesc);
        StyleCol.add(Box.createVerticalStrut(10));
        StyleCol.add(HuntAndPeck);
        StyleCol.add(HuntAndPeckDesc);




       
       JPanel SymbolCol= new JPanel();
       SymbolCol.setLayout(new BoxLayout(SymbolCol,BoxLayout.Y_AXIS));
      SymbolCol.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       SymbolCol.setBackground(Color.decode("#FFF0F5"));
       JLabel symbolTitle= new JLabel("SYMBOL");
       symbolTitle.setFont(new Font("Verdana", Font.BOLD,13));
       symbolTitle.setForeground(Color.decode("#AF7F73"));
       symbolTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

       String[] symbols = {"①","②","③","④","⑤","⑥"};
       ButtonGroup symbolGroup = new ButtonGroup();
       JRadioButton[] symbolButtons= new JRadioButton[6];

       SymbolCol.add(Box.createVerticalStrut(10));
       SymbolCol.add(symbolTitle);
       SymbolCol.add(Box.createVerticalStrut(5));

       for(int i=0;i<6;i++)
       {
        String s=symbols[i];
        JRadioButton btn = new JRadioButton(s);
        btn.setBackground(Color.decode("#FFF0F5"));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Sergoe UI Symbol", Font.PLAIN, 20));
        
        if(symbolTaken[i])
        {
          btn.setEnabled(false);
          btn.setForeground(Color.GRAY);

        }

        if(typistSymbols[PlayerNum-1]!=null && typistSymbols[PlayerNum-1].equals(symbols[i]))
        {
          btn.setSelected(true);
        }

        symbolGroup.add(btn);
        symbolButtons[i]=btn;
        SymbolCol.add(btn);
       }

       


          JPanel ColourCol= new JPanel();
       ColourCol.setLayout(new BoxLayout(ColourCol,BoxLayout.Y_AXIS));
      ColourCol.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      ColourCol.setBackground(Color.decode("#FFF0F5"));
       ColourCol.add(new JLabel("Typist Colour"));

       JLabel colourTitle=new JLabel("COLOUR");
       colourTitle.setFont(new Font("Verdana",Font.BOLD,13));
       colourTitle.setForeground(Color.decode("#AF7F73"));
       colourTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

       
       
       String[] colourNames={"Red", "Blue", "Orange", "Green", "Purple", "Pink"};
       Color[] colourValue={Color.RED,Color.BLUE,Color.ORANGE,Color.GREEN,Color.decode("#dd00ff"),Color.PINK};
       ButtonGroup Colours = new ButtonGroup();

            ColourCol.add(Box.createVerticalStrut(10));
       ColourCol.add(colourTitle);
       ColourCol.add(Box.createVerticalStrut(5));
       for(int i=0; i<colourNames.length;i++)
       {
        JPanel addRow= new JPanel(new FlowLayout(FlowLayout.LEFT));
        addRow.setBackground(Color.decode("#FFF0F5"));
        addRow.setMaximumSize(new Dimension(150,25));
        addRow.setPreferredSize(new Dimension(150,25));
        JProgressBar progressBar = new JProgressBar();
        JRadioButton newButton = new JRadioButton();
          progressBar.setValue(100);
          progressBar.setForeground(colourValue[i]);
          progressBar.setBackground(Color.LIGHT_GRAY);
          progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
          progressBar.setPreferredSize(new Dimension(80,15));
          progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
          
          final int colourI = i;
          newButton.addActionListener(ev -> selectedColourIndex[PlayerNum - 1] = colourI);

          Colours.add(newButton);
          addRow.add(newButton);
          addRow.add(progressBar);
          ColourCol.add(addRow);

           if(i==0)
          {
            newButton.setSelected(true);
          }
          if(ColourTaken[i])
          {
            newButton.setEnabled(false);
            newButton.setForeground(Color.LIGHT_GRAY);
          }

    

       }

       

       




      
           JPanel KeyboardCol = new JPanel();
       KeyboardCol.setLayout(new BoxLayout(KeyboardCol,BoxLayout.Y_AXIS));
      KeyboardCol.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      KeyboardCol.setBackground(Color.decode("#FFF0F5"));
       KeyboardCol.add(new JLabel("Typist Keyboard"));

       JLabel keyboardTitle=new JLabel("KERBOARD");
       keyboardTitle.setFont(new Font("Verdana",Font.BOLD,13));
       keyboardTitle.setForeground(Color.decode("#AF7F73"));
       keyboardTitle.setAlignmentX((Component.CENTER_ALIGNMENT)); 

       JRadioButton mechanical= new JRadioButton("Mechanical");
       mechanical.setBackground(Color.decode("#FFF0F5"));
       mechanical.setAlignmentX(Component.CENTER_ALIGNMENT);
       JTextArea mechDesc= new JTextArea("Good keys . 0.05 accuracy");
       mechDesc=setTextArea(mechDesc);

      JRadioButton touchSc=new JRadioButton("Touch Screen");
      touchSc.setBackground(Color.decode("#FFF0F5"));
      touchSc.setAlignmentX(Component.CENTER_ALIGNMENT);

      JTextArea touchScDesc=new JTextArea("");
      touchScDesc=setTextArea(touchScDesc);

      ButtonGroup keyboardGroup = new ButtonGroup();
      keyboardGroup.add(mechanical);
      keyboardGroup.add(touchSc);
      mechanical.setSelected(true);

      KeyboardCol= AddtoColumn(KeyboardCol, keyboardTitle, touchSc, mechanical, touchScDesc, mechDesc);


       

        JPanel AccessoriesCol = new JPanel();
       AccessoriesCol.setLayout(new BoxLayout(AccessoriesCol,BoxLayout.Y_AXIS));
      AccessoriesCol.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      AccessoriesCol.setBackground(Color.decode("#FFF0F5"));
       AccessoriesCol.add(new JLabel("Typist Accessories"));

       JCheckBox wristBox= new JCheckBox("Wrist Support");
       JCheckBox energyBox = new JCheckBox("Energy Drink");

       JLabel accTitle= new JLabel("ACCESSORIES");
       accTitle.setFont(new Font("Verdana", Font.BOLD, 13));
       accTitle.setForeground(Color.decode("#AF7F73"));
       accTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

       wristBox.setBackground(Color.decode("#FFF0F5"));
       wristBox.setAlignmentX(Component.CENTER_ALIGNMENT);
          wristBox.setSelected(wristSupport[PlayerNum-1]);
          JLabel wristDesc = new JLabel("shorter burnout duration");
          wristDesc.setFont(new Font("Verdana",Font.ITALIC,10));
          wristDesc.setForeground(Color.decode("#AF7F73"));
          wristDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
      
          
      energyBox.setBackground(Color.decode("#FFF0F5"));
       energyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        energyBox.setSelected(energyDrink[PlayerNum-1]);
          JLabel energyDesc = new JLabel("shorter burnout duration");
          energyDesc.setFont(new Font("Verdana",Font.ITALIC,10));
          energyDesc.setForeground(Color.decode("#AF7F73"));
          energyDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

          AccessoriesCol= AddtoColumn2(AccessoriesCol, accTitle, energyBox, wristBox, energyDesc, wristDesc);

          JButton nextButton = new JButton("NEXT");
          nextButton.setBackground(Color.decode("#f5d9ea"));
          nextButton.setForeground(Color.decode("#AF7F73"));
          nextButton.setFont(new Font("Verdana",Font.ITALIC,18));

          nextButton.addActionListener(e->{

            //check if the symbol buttons taken and which one and save them
            for(int i=0; i<symbolButtons.length;i++)
            {
              if(symbolButtons[i].isSelected())
              {
                typistSymbols[PlayerNum-1]=symbols[i];
                symbolTaken[i]=true;
                break;
              }
            }

             // save colour

              
              typistColours[PlayerNum - 1] = colourValue[selectedColourIndex[PlayerNum - 1]];
             ColourTaken[selectedColourIndex[PlayerNum - 1]] = true;


             //save Accuracy
             double acc=0.85;//default for touch typist
             if(touchTypist.isSelected())
             {
               acc=0.85;
             }

             else
             {
              acc=0.55;
             }

             if(mechanical.isSelected())
             {
              acc+=0.05;
             }
             else
             {
              acc-=0.10;
             }

             if(energyBox.isSelected())
             {
              acc+=0.05;
             }

             typistAccuracies[PlayerNum-1]=acc;

            frame.dispose();
            if(PlayerNum<minPlayers)
            {
              CustomiseTypists(PlayerNum +1);
            }

            else
            {
              Race();
            }
          });

          bottom.add(nextButton,BorderLayout.EAST);


       mainPanel.add(StyleCol);
       mainPanel.add(SymbolCol);
       mainPanel.add(ColourCol);
       mainPanel.add(KeyboardCol);
       mainPanel.add(AccessoriesCol);
       frame.add(mainPanel,BorderLayout.CENTER);
       frame.setVisible(true);
  }

  public JTextArea setTextArea(JTextArea a) //used to set the same properties for all the text areas in the customisation page without repeating code
  {
        a.setEditable(false);
        a.setOpaque(false);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
       a.setFont(new Font("Verdana", Font.ITALIC,10));
        a.setForeground(Color.decode("#AF7F73"));
        a.setMaximumSize(new Dimension(130,40));

        return a;
  }

  public JPanel AddtoColumn(JPanel p, JLabel title, JRadioButton one, JRadioButton two, JTextArea desc1, JTextArea desc2) //used to add the same components in the same order to the keyboard and style columns without repeating code
  {
     p.add(Box.createVerticalStrut(10));
     p.add(title);
     p.add(Box.createVerticalStrut(10));
     p.add(one);
     p.add(desc1);
     p.add(Box.createVerticalStrut(10));
     p.add(two);
     p.add(desc2);

     return p;
  }

    public JPanel AddtoColumn2(JPanel p, JLabel title, JCheckBox one, JCheckBox two, JLabel desc1, JLabel desc2) //used to add code in column for accessories column as it uses checkboxes and labels instead of radio buttons and text areas
  {
     p.add(Box.createVerticalStrut(10));
     p.add(title);
     p.add(Box.createVerticalStrut(15));
     p.add(one);
     p.add(desc1);
     p.add(Box.createVerticalStrut(10));
     p.add(two);
     p.add(desc2);

     return p;
  }


  public void Race() //displays the race page with progress bars and simulates the race using a timer( that updates every second) also checks for progress, mistypes and burnouts based on the typist's accuracy and the enabled difficulty modifiers
  {
       for(int i=0; i<6;i++)
      {
        burnouts.add(0);
        mistypes.add(0);
        correctOnes.add(0);
        pbs.add(0.0);
      }
    JFrame frame= new JFrame("TypingRace");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800,500);
    frame.setLayout(new BorderLayout());
    JPanel titlePanel= new JPanel();
    Title(titlePanel, "TYPING RACE");
    frame.add(titlePanel, BorderLayout.NORTH);
    //display text
    JTextArea Area= new JTextArea(selectedPassage);
    Area.setFont(new Font("Verdana", Font.PLAIN,14));
    Area.setEditable(false);
    Area.setLineWrap(true);//goes down in space not just in a lines
    Area.setBackground(Color.decode("#FFF0F5"));
    frame.add(Area,BorderLayout.CENTER);
    JPanel race= new JPanel(new GridLayout(minPlayers,1));
    JProgressBar[] progressbars= new JProgressBar[minPlayers];
    JLabel[] Output= new JLabel[minPlayers];

    for(int i=0; i<minPlayers;i++)
    {
      String s="①";
      if(typistSymbols[i]!=null)
      {
        s=typistSymbols[i];
      }
      JPanel row= new JPanel(new BorderLayout());
      JLabel whichPlayer =new JLabel(s + " Player" + (i+1)); //display which player is  playing
      whichPlayer.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
      JProgressBar Player_progress = new JProgressBar(0, selectedPassage.length());
      Player_progress.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());//lets bar react like a regular 
      Player_progress.setBackground(Color.LIGHT_GRAY); //unfilled space is greyed
      Color c=Color.CYAN;//default color if not picked
      if(typistColours[i]!= null)
      {
         c= typistColours[i];
      }
      Player_progress.setForeground(c);//set the progress bar colour on top to be the player colour that they chose
      JLabel EachTypistsSituation = new JLabel();
      EachTypistsSituation.setPreferredSize(new Dimension(100,30));
      progressbars[i]=Player_progress;
      Output[i]=EachTypistsSituation;
      row.add(whichPlayer,BorderLayout.WEST);
      row.add(Player_progress,BorderLayout.SOUTH);
      row.add(EachTypistsSituation);
      race.add(row);
    }
      frame.add(race,BorderLayout.SOUTH);
      frame.setVisible(true);
      Typist[] typists=new Typist[minPlayers];//make typist characters/other data from saved choices
      for(int i=0; i<minPlayers;i++)
      {
        String symbol ="①";

        if(typistSymbols[i]!=null)
        {
          symbol=typistSymbols[i];
        }
        typists[i]=new Typist(symbol,"player"+(i+1),typistAccuracies[i]);
        if(nightshiftEnabled)
        {
          typists[i].setAccuracy(typistAccuracies[i]-0.10);

        }
      }

        int slideback=2; //automatically 2 if no option selected
        if(autocorrectEnabled)
        {
          slideback=1;
        }

        int[] turns={0};//array used to allow changes in lambda 
        int passagelength=selectedPassage.length();
        final int Slideback= slideback;
        javax.swing.Timer timer = new javax.swing.Timer(1000,null);//runs every 1000ms / so every second
        timer.addActionListener(e->{
          turns[0]++;

          for(int i=0;i<minPlayers;i++)
          {
             if(typists[i].isBurntOut())
             {
              typists[i].recoverFromBurnout();
              Output[i].setText(" BURNT OUT");
              continue; //skip the rest as player cannot move
             }

             double accuracy = typists[i].getAccuracy();
             if(caffeineEnabled && turns[0]<=10) //boost for first 10 turns
             {
                accuracy+=0.1;
                if(accuracy>0.1)
                {
                  accuracy=1.0;
                }
              }

                if(Math.random()<accuracy) //check if type is successful
                {
                  typists[i].typeCharacter();
                  correctOnes.set(i, correctOnes.get(i) + 1);
                }

                if(Math.random()<(1-accuracy)*0.3) //check if mistype happens
                {
                  typists[i].slideBack(Slideback);
                  Output[i].setText("MISTYPED");
                  mistypes.set(i, mistypes.get(i) + 1);
                }

                else
                {
                  Output[i].setText("");
                }

                int burnoutlength=3;
                if(wristSupport[i])
                {
                  burnoutlength=1;//if wrist support activated then burnout turns are 1
                }

                if(Math.random()<0.05*accuracy*accuracy)
                {
                     typists[i].burnOut(burnoutlength); //burnout turns are set
                     burnouts.set(i, burnouts.get(i) + 1);

                }
                progressbars[i].setValue(typists[i].getProgress());//set progress bar to progress level
              }
                
             

          for(int i=0;i<minPlayers;i++)
          {
            if(typists[i].getProgress()>=passagelength)//check if someone won by seeing if progress is either passage len or beyond
            {
              timer.stop();
              Output[i].setText(" Player "+(i+1)+" WINNER");
              frame.dispose();
              Stats(typists, turns[0], i);
              return;
            }
          }
        });

        timer.start();
      

    
  
    
  }

  public void Stats(Typist[]typists, int turns, int winnerIndex)//displays stats after the race 
  {
      

     long end= System.currentTimeMillis();
     double time= (end-start)/60000.0;// converted to mins 
     JDialog dialog = new JDialog();
     dialog.setTitle("STATS");
     dialog.setSize(600,400);
      dialog.setLayout(new BorderLayout()); 
      JPanel TitlePanel= new JPanel();
       Title(TitlePanel, "Race Stats");
       dialog.add(TitlePanel, BorderLayout.NORTH);
       JPanel mainPanel = new JPanel(new GridLayout(1,minPlayers));
       mainPanel.setBackground(Color.decode("#FFF0F5"));
       for(int i=0; i<minPlayers;i++)
       {
        JPanel column= new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setBorder(BorderFactory.createLineBorder(Color.decode("#AF7F73")));
        column.setBackground(Color.decode("#FFF0F5"));
        double wpm=selectedPassage.length()/(5.0);// (a word is 5 characters)
         int keyspressed=correctOnes.get(i)+mistypes.get(i); //all keys pressed are corrrect and incorrect ones
         double accPercent=0;
          if(keyspressed>0)
          {
            accPercent= (double)correctOnes.get(i)/keyspressed*100;
          }
          if(keyspressed>0)
          {
            accPercent=(correctOnes.get(i)/(double)keyspressed)*100;
          }
          if(wpm>pbs.get(i))
          {
            pbs.set(i, wpm);
          }
          double changeInAcc=0;
          if(i==0)
          {
            changeInAcc=0.01;
          }
          if(burnouts.get(i)>0)
          {
            changeInAcc-=0.01;
          }

          typists[i].setAccuracy(typistAccuracies[i]+changeInAcc);
          typistAccuracies[i]=typists[i].getAccuracy();
          String s;
           if(typistSymbols[i]!=null)
           {
             s=typistSymbols[i];
           }
           else
           {
             s="①";
           }
           

           column.add(Box.createVerticalStrut(10));
               JLabel labelSymbol = new JLabel(s,SwingConstants.CENTER);
              labelSymbol.setForeground(Color.decode("#AF7F73"));
              labelSymbol.setFont(new Font("Sergoe UI Symbol", Font.PLAIN, 20));
              labelSymbol.setText("Player " + (i+1) + " " + s);
           column.add(labelSymbol);
           column.add(Box.createVerticalStrut(10));
            column.add(Stat("WPM: " + String.format("%.2f", wpm)));
            column.add(Box.createVerticalStrut(10));
             column.add(Stat("Accuracy: " + String.format("%.2f", accPercent) + "%"));
             column.add(Box.createVerticalStrut(10));
              column.add(Stat("Burnouts: " + burnouts.get(i)));
              column.add(Box.createVerticalStrut(10));
               column.add(Stat("Mistypes: " + mistypes.get(i)));
        mainPanel.add(column);

        double[] prizes={100,75,50,25,10,5};
        for(int j=0; j<minPlayers;j++)
        {
          double earned=prizes[i];
          if(burnouts.get(i)==0)
          {
            earned+=50;
            System.out.println("Player " + (i+1) + " earned a sponsor from sponsors[i]");

          }
          else
          {
            earned-=10*burnouts.get(i);
          }

          double WPM=(selectedPassage.length()/5.0)/time;
          if(WPM>30)
          {
            earned+=25;

          }
          if(earned<0)
          {
            earned=0;
          }
          earnings[i]+=earned;
          
        }
        winsInaRow[0]++;
        for(int j=1;j<minPlayers;j++)
        {
          winsInaRow[j]=0;
         }
         for(int j=0;j<minPlayers;j++)
         {
          if(burnouts.get(j)==0)
          {
            noBurnoutStreak[j]++;
          }
          else
          {
            noBurnoutStreak[j]=0;
          }
         }
        dialog.add(mainPanel, BorderLayout.CENTER);
        JPanel bottom= new JPanel();
        JButton close= new JButton("CLOSE");
        close.setBackground(Color.decode("#f5d9ea"));
        close.setForeground(Color.decode("#AF7F73"));
        close.setFont(new Font("Verdana", Font.ITALIC, 18));
        close.addActionListener(e-> 
          {
            dialog.dispose();
            HomePage();
  });
  JButton leaderboard=new JButton("LEADERBOARD");
        leaderboard.setBackground(Color.decode("#f5d9ea"));
        leaderboard.setForeground(Color.decode("#AF7F73"));
        leaderboard.setFont(new Font("Verdana", Font.ITALIC, 18));
        leaderboard.addActionListener(e-> 
          {
            dialog.dispose();
            Leaderboard();
          });
          JButton sponsor=new JButton("SPONSORS");
        sponsor.setBackground(Color.decode("#f5d9ea"));
        sponsor.setForeground(Color.decode("#AF7F73"));
        sponsor.setFont(new Font("Verdana", Font.ITALIC, 18));
        sponsor.addActionListener(e-> 
          {
            dialog.dispose();
            Sponsors();
          });

          if(i==winnerIndex)
          {
             JLabel winnerLabel = new JLabel("WINNER", SwingConstants.CENTER);
             winnerLabel.setFont(new Font("Verdana", Font.BOLD, 16));
              winnerLabel.setForeground(Color.RED);
              winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
              mainPanel.add(winnerLabel);
              column.add(winnerLabel);
          }
        bottom.add(leaderboard);
        bottom.add(sponsor);
        bottom.add(close);
        dialog.add(bottom, BorderLayout.SOUTH);
        dialog.setVisible(true);
      }

  }

  public JLabel Stat(String t) //used to create stats page label
  {
    JLabel label = new JLabel(t,SwingConstants.CENTER);
    label.setFont(new Font("Verdana", Font.PLAIN, 14));
    label.setForeground(Color.decode("#AF7F73"));
    return label;
  }

  public void Leaderboard()//displays the leaderboard page with the players sorted by their points and titles based on their points as well as buttons to go to sponsors page and back to home page
  {
    JFrame frame=new JFrame("Leaderboard");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,300);
    JPanel TitlePanel= new JPanel();
    Title(TitlePanel, "LEADERBOARD");
    frame.add(TitlePanel, BorderLayout.NORTH);
    JPanel mainPanel= new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(Color.decode("#FFF0F5"));
    int[]order={0,1,2,3,4,5};//sort by points
    for(int i=0;i<6;i++)
    {
      for(int j=0;j<5-i;j++)
      {
        if(pbs.get(j)<pbs.get(j+1))
        {
          int temp=order[j];
          order[j]=order[j+1];
          order[j+1]=temp;
        }
      }


    }
    int rank=1;//first place
    String specialTitle="";
           for(int i=0;i<minPlayers;i++)
           {
             int idx=order[i];
             
             if(pbs.get(idx)==0)
             {
              continue;//skip players with no points
             }
             String s;
             if(typistSymbols[idx]!=null)
             {
              s=typistSymbols[idx];
             }
             else
             {
              s="①";
             }

             if(pbs.get(idx)>=6)
             {
                specialTitle=" SPEED DEMON";

             }

             else if(pbs.get(idx)>=3)
             {
              specialTitle=" IRON FINGERS";
             }
              JLabel label = new JLabel(rank + ". Player " + (idx+1) + " " + s + " - WPM: " + String.format("%.2f", pbs.get(idx)) + specialTitle, SwingConstants.CENTER);
              label.setFont(new Font("Verdana", Font.PLAIN, 14));
              label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
              label.setForeground(Color.decode("#AF7F73"));
              mainPanel.add(label);
              rank++;
           }
           

           frame.add(mainPanel, BorderLayout.CENTER);
           JPanel bottom= new JPanel();
           JButton sponsor=new JButton("SPONSORS");
        sponsor.setBackground(Color.decode("#f5d9ea"));
        sponsor.setForeground(Color.decode("#AF7F73"));
        sponsor.setFont(new Font("Verdana", Font.ITALIC, 18));
        sponsor.addActionListener(e->
          {
            frame.dispose();
            Sponsors();
          });
        JButton back= new JButton("BACK");
        back.setBackground(Color.decode("#f5d9ea"));
        back.setForeground(Color.decode("#AF7F73"));
        back.setFont(new Font("Verdana", Font.ITALIC, 18));
        back.addActionListener(e-> 
          {
            frame.dispose();
            for(int i=0;i<6;i++)
            {
              burnouts.set(i,0);
              mistypes.set(i,0);
              correctOnes.set(i,0);
            }
            HomePage();
          });
          bottom.add(back);
          frame.add(bottom, BorderLayout.SOUTH);
           frame.setVisible(true);
        }

  public void Sponsors()//displays the sponsors page with the players sorted by their earnings and deals based on their earnings as well as buttons to go to leaderboard page and back to home page
  {
     JFrame frame=new JFrame("Sponsors");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500,400);
      JPanel TitlePanel= new JPanel();
      Title(TitlePanel, "SPONSORS & EARNINGS");
      frame.add(TitlePanel, BorderLayout.NORTH);
      JPanel mainPanel= new JPanel();
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setBackground(Color.decode("#FFF0F5"));
      int[]order={0,1,2,3,4,5};//sort by earnings
      for(int i=0;i<6;i++)
      {
        for(int j=0;j<5-i;j++)
        {
          if(earnings[order[j]]<earnings[order[j+1]])
          {
            int temp=order[j];
            order[j]=order[j+1];
            order[j+1]=temp;
          }
        }

      }
      int rank=1;
      for(int i=0;i<minPlayers;i++)
      {
        int idx=order[i];
        
        if(earnings[idx]==0)
        {
          continue;//skip players with no earnings
        }
        String s;
        if(typistSymbols[idx]!=null)
        {
          s=typistSymbols[idx];
        }
        else
        {
          s="①";
        }
        String deal= sponsors[idx] + "  $50 for no burnouts";
        JPanel row= new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setBackground(Color.decode("#FFF0F5"));
         row.setBorder(BorderFactory.createLineBorder(Color.decode("#AF7F73")));
         row.setAlignmentX(Component.CENTER_ALIGNMENT);
         JLabel label = new JLabel(rank + ". Player " + (idx+1) + " " + s + " - Earnings: $" + String.format("%.2f", earnings[idx]), SwingConstants.CENTER);
              label.setFont(new Font("Verdana", Font.PLAIN, 14));
              label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
              label.setForeground(Color.decode("#AF7F73"));
              JLabel dealLabel = new JLabel("Sponsor: " + deal);
              dealLabel.setFont(new Font("Verdana", Font.ITALIC, 12));
              dealLabel.setForeground(Color.decode("#AF7F73"));
              dealLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
              row.add(label);
              row.add(dealLabel);
              mainPanel.add(Box.createVerticalStrut(10));
              mainPanel.add(row);
              rank++;
      }

      frame.add(mainPanel, BorderLayout.CENTER);
      JPanel bottom= new JPanel();
      JButton leaderboard=new JButton("LEADERBOARD");
        leaderboard.setBackground(Color.decode("#f5d9ea"));
        leaderboard.setForeground(Color.decode("#AF7F73"));
        leaderboard.setFont(new Font("Verdana", Font.ITALIC, 18));
        leaderboard.addActionListener(e-> 
          {
            frame.dispose();
            Leaderboard();
          });
        JButton back= new JButton("BACK");
        back.setBackground(Color.decode("#f5d9ea"));
        back.setForeground(Color.decode("#AF7F73"));
        back.setFont(new Font("Verdana", Font.ITALIC, 18));
        back.addActionListener(e-> 
          {
            frame.dispose();
            for(int i=0;i<6;i++)
            {
              burnouts.set(i,0);
              mistypes.set(i,0);
              correctOnes.set(i,0);
            }
            HomePage();
          });
          bottom.add(back);
           bottom.add(leaderboard);
           frame.add(bottom, BorderLayout.SOUTH);
            frame.setVisible(true);
            
              
    }
  



  }






  





