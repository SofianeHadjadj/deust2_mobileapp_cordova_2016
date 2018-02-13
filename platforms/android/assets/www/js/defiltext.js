function defiltext() {
	//largeur du texte (en pixels)
	//var marqueewidth=150;
	//hauteur du texte défilant
	//var marqueeheight=150;
	//vitesse de défilement 
	var speed=2;
	//contenu
	var marqueecontents='<div style="background-color: blue;"><p>Test - test</p><p>Test2 - test2</p></div>';

	document.write('<body id="secondBody"><marquee direction="up" scrollAmount='+speed+'; style="width:60%;height:60%">'+marqueecontents+'</marquee></body>');

	function regenerate(){
	window.location.reload();
	}

	function intializemarquee(){
	document.cmarquee01.document.cmarquee02.document.write(marqueecontents);
	document.cmarquee01.document.cmarquee02.document.close();
	thelength=document.cmarquee01.document.cmarquee02.document.height;
	scrollit();
	}

	function scrollit(){
	if (document.cmarquee01.document.cmarquee02.top>=thelength*(-1)){
	document.cmarquee01.document.cmarquee02.top-=speed;
	setTimeout("scrollit()",100);
	}
	else{
	document.cmarquee01.document.cmarquee02.top=marqueeheight;
	scrollit();
	}
	}
}

function musicFunc() {
    document.getElementById("music").innerHTML = '<object type="audio/mpeg" width="0" height="0" loop="true" data="sound.mp3"><param name="audio" value="sound.mp3"><param name="controller" value="false"><param name="autostart" value="true"/><param name="PLAY" value="true"><param name="LOOP" value="true"/></object>';
}

window.onload=function(){defiltext();musicFunc();}