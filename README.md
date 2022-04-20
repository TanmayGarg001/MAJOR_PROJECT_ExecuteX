# ExecuteX
# ExecuteX  _[Updates: Version Changes]_
--------------------------------------------------------                                                                                            				
**v0.1**       
= Added dependancy for toggleable edit text in build.gradle app.//which is depriciated now, so instead used android material:1.5.0     
= Added Bg color and defined other colors for loginPage using different themes.      
= Added reference line at center mid as a relative layout to achieve responsive layout.        
= Added Login message and email,password EditTexts,Login button and forgot password.         
= Made changes to design and abstracted colors,strings from activity_main.xml to colors.xml and strings.xml respectively. 

--------------------------------------------------------

**v0.1.1**   
= Added forgotPassword and singUp activity along with respective layouts.        
= Changed fonts for the UI, used Google fonts.           
= Set up on click listeners using anonymous class.                            
= Made changes to design and abstracted colors,strings from all xmls to colors.xml and strings.xml                      
= Basic functionality, good design and manueveralbilty achieved.                          
= Fixed typos and added comments.                           

--------------------------------------------------------

**v0.2**                    
= Added functionality and validation checks in ForgotPasswordActivity. Validation check uses regex which works 99% of the time.No regex is perfect for emailId check.     
= Added password validation and minimum password requirements.                  
= Added vibrations to buttons and specific text for good user experience.                                               
= Made all member variables private adhering to encapsulation.                                            
= Added Utilities class that abstracts away the validation checks for other activities.                               
= Used lambda expression instead of Anonymous new View.OnClickListener() coz there's no req. for Anonymous inner class.                        
= Minor changes in design.                    
= Fixed typos and added comments.                     

--------------------------------------------------------       

**v0.3**    
= Firebase projectID : executex-31XX7, link: https://console.firebase.google.com/u/0/project/executex-31XX7/overview    
= Added JSON file to project/app directory.    
= Linked to southAsia1 server for database and added dependancies for firebase authentication and firestore in android studio using Tools>Firebase>Authentication.     
= Added emailVerification and process of creating a new user using firebase.    
= Added resetEmail and verifcation for forgot password.   
= Added login verification for existing user.   
= Minor changes in design and fixed typos.    
= Added more comments for self-documenting purposes.    
