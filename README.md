# DS4DM-Backend

The DS4DM-Backend is a webservice which works in conjunction with the <a href="https://community.rapidminer.com/t5/Community-Blog/The-Data-Search-for-Data-Mining-Extension-Release/ba-p/38231">Data Search for Data Mining (DS4DM) RapidMiner Extension</a>. The memory-intensive and processing-intensive functionalities of the DS4DM RapidMiner Extension have been outsourced to the DS4DM Backend. This includes various Data Searches, Data pre-processing functions, Data Repository management functions,... - for more informatioin, please refer to the <a href="http://web.informatik.uni-mannheim.de/ds4dm/">website of the DSDM Backend</a>.<br>
<br>

## Installation/Setup
<ol>
  <li>download this github repository
  <li>make sure that the is in folder with Java.exe is in the Path
  <li>open the a terminal and execute:<br>
    <table frame="box">
      <tr>
        <th>
          cd <div style="color:blue;">&lt;path_to_downloaded_folder&gt;</div>/DS4DM-Backend/DS4DM-Webservice/DS4DM_webservice<br>
          java -Xms1024m -Xmx1024m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -jar activator-launch-1.2.12.jar "run -Dhttp.port=9004"
        </th>
      </tr>
    </table><br>
   <li>In your RapidMiner-process you now set url-Parameter of the Data Search operator to "http://localhost:9004".<br>
     <img class="img-responsive" src="http://web.informatik.uni-mannheim.de/ds4dm/images/Set_URL_in_Data_Search_operator.png" alt="keyword-based search" height="350" width="900" align="middle"  style="display: block; margin-left:auto; margin-right: auto;z-index: 1;">  
</ol>
<br>

## Other Resources:
<ul>
  <li><a href="http://ds4dm.de/">Official DS4DM website</a>
  <li><a href="http://web.informatik.uni-mannheim.de/ds4dm/">Website for the DSDM Backend</a>
  <li><a href="http://dws.informatik.uni-mannheim.de/en/projects/ds4dm-data-search-for-data-mining/">Website from Mannheim University</a>
</ul>


