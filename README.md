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
                                                
--------------------------------------------------------                              
                                              
**v0.4**                                          
= Added layouts for newTask and Cardview using Adapters.                                          
= Added logout functionality using task_list.xml menu.                                                  
= Added functionality for deleting the task.                                                     
= Minor changes in design and fixed typos.                                          
= Added CreateTask class to store task details in Database.                                                
= Major changes to UI.                                   
= Added icons, vector assests and more for design purposes.                                             
= Added more comments for self-documenting purposes.                                       
= Gradle upgraded from 7.1.2 to 7.1.3                
            
--------------------------------------------------------                                                  
                                                                                                            
**v0.5**                                                                                                         
= Inverted logic for Utilities Class.                                                                               
= Added control flow, logic, variables and objects etc. to CreateTaskActivity.                                                                      
= Changed read write rule.                                                                                           
= Added Cloud Firestore functionality using DocumentReference : refers to a document location in a Cloud Firestore database and can                                
be used to write, read, or listen to the location.                         
= Added dependancy for FireBase UI in build.gradle app.                                              
= Added fetching of data from Cloud Firestore to Recycler View using GridLayoutManager and FirestoreRecyclerAdapter.                                        
= Fixed typos and added comments.        
                  
--------------------------------------------------------            

**v0.6**               
= Fixed Major bug of null title and description and problem in fetching data from Cloud FireStore Database.                                
= Added taskOpt options and rendomized colors for task.                                
= More classes anbd layouts for Editing Task, Read Only Task added.                                
= Delete functionality added.                                
= Progress Bar added for good user experience.                                 
= Changes to UI and added more comments, fixed typos.                                

--------------------------------------------------------                                                     
                                                        
**v1.0_StableBuild**                
= App is now fully functional, all the layouts and activities work as they are supposed to do.                                                      
= Added tons of comments to save time for future references and self-documenting purposes.                                                      
= Optimized code using lambdas expressions and other various practices.                                                     
= Designed the application icon for android.                                                      
= Tested compatability on various android devices.                                                      
= Fixed minor bugs and multiple warnings.                                                     
= Changed some variable names and corrected typos.                                                      
= Gradle upgraded from 7.1.2 to 7.2.0                                                     
                                        
--------------------------------------------------------     
                                    
**v1.1_StableBuild**                                              	 
= Added Speech to text functionality for easier-usage and to increase user base.                                         
= Changed layouts of various XML files.                                   
= Renamed references, changed control-flow and optimized code using lambda expressions.                                                
= Tested compatability on various android devices.                                       
= Fixed minor bugs and multiple warnings.                                 
= Added comments for future references and self-documenting purposes.                                      
= Gradle upgraded from 7.1.2 to 7.2.1           
                                        
--------------------------------------------------------            
        
