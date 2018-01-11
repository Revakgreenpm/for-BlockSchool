//Written in processing3

import processing.sound.*;
SinOsc sine1;
SinOsc sine2;

int note=1;

int fullnote=2000;
int halfnote=1000;
int quarternote=500;
int eightnote=250;
int dottedfull=3000;
int dottedhalf=1500;
int dottedquart=750;
int dottedeight=375;
float freq=110;
float root=freq;
  float second=root*1.12242479;
  float third = second*1.12242479;
  float fourth = third*(1+(.122424798/2));
  float fifth = fourth*.12242479;
  float sixth= fifth*.12242479;
  float seventh=sixth*.12242479;
  float eigth=seventh*(1+(.122424798/2));
  float[] scale={root, second, third, fourth, fifth, sixth, seventh, eigth};
  int[] octave={1, 2, 3, 4};
  int[] beats={fullnote, halfnote, quarternote, eightnote, dottedfull, dottedhalf, dottedquart, dottedeight};

void setup() {
  size(640, 360);
  background(255);
  
  sine1 = new SinOsc(this);
  sine2 = new SinOsc(this);
  sine1.play();
  sine2.play();
 
  
  
  
  
}      

void draw() {
  
  
  sine1.freq(freq);
  sine2.freq(freq*1.02);
  
  int index1=int(random(scale.length));
  int index2=int(random(octave.length));
  int index3=int(random(beats.length));

  freq=scale[index1]*octave[index2];
  
  
  delay(500);
  
  
  
}
