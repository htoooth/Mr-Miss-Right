class YuanController < ApplicationController
    # GET /softwares
  # GET /softwares.xml
  def auto_save       
 
     userid = params[:userid];
     posx =  params[:posx];
     posy = params[:posy];
     date = params[:date];
     File.open("../data.update", "wb") do |f|        
            f.write("#{userid}\n#{posx}\n#{posy}\n#{date}\n") 
     end
     system 'cp ../abc.txt ../aaa.txt'
     #f.puts(" #{userid}#{posx}#{posy}#{date}#{islike}")
     str=userid+"  "+posx+" "+posy+" "+date;
    render (:text=>"#{str}")
  end
  
  def like_save       
 
       userid = params[:userid];
     posx =  params[:posx];
     posy = params[:posy];
     date = params[:date];
     File.open("../data.like", "wb") do |f|        
            f.write("#{userid}\n#{posx}\n#{posy}\n#{date}\n") 
     end
     #system 'cp ../abc.txt ../aaa.txt'
     #f.puts(" #{userid}#{posx}#{posy}#{date}#{islike}")
     str=userid+"  "+posx+" "+posy+" "+date;
    render (:text=>"#{str}")
  end

  def search
    
     userid = params[:userid];
     posx =  params[:posx];
     posy = params[:posy];
     date = params[:date];
     File.open("../data.search", "wb") do |f|        
            f.write("#{userid}\n#{posx}\n#{posy}\n#{date}\n") 
     end
     system 'cp ../abc.txt ../search.ans'
     #f.puts(" #{userid}#{posx}#{posy}#{date}#{islike}")
   
     str="";
     File.open("../search.ans", "r") do |f|        
          str+=f.read(); 
     end
     
       render (:text=>"#{str} ")
  end
  
end
