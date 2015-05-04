package org.i3xx.util.basic.util.text;

/*
 * #%L
 * NordApp OfficeBase :: util :: basic
 * %%
 * Copyright (C) 2015 I.D.S. DialogSysteme GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class QuotedStringTokenizer implements Enumeration<String>
{

	private List<String> tokens;
	private Iterator<String> iter;


	//comma separated, Strings quoted (")
	public QuotedStringTokenizer(String str)
	{
		this(str, ",", "\"");
	}

	//any separator char, Strings quoted (")
	public QuotedStringTokenizer(String str, String delim)
	{
		this(str, delim, "\"");
	}

	//Unquoted Strings are skipped
	public QuotedStringTokenizer(String str, String _delim, String _clip)
	{
		tokens = new ArrayList<String>();
		iter = null;
		
		char clip=_clip.charAt(0);
		char delim=_delim.charAt(0);
		char wrap='\\';
		StringBuffer buf=new StringBuffer(str);
		StringBuffer res=new StringBuffer();
		
		boolean k=false; //Bracket
		boolean g=false; //Bracket closed
		
		for(int i=0;i<buf.length();i++) {
			char c=buf.charAt(i);
			
			//wrap char
			if(c==wrap){
				i++;
				continue;
			}
			
			if( (!k) && c==clip){
				//Quotas
				res=new StringBuffer();
				k=true;
			}else if(k && c==clip){
				//End char
				k=false;
				g=true;
				tokens.add(res.toString());
				res=new StringBuffer();
			}else if( (!k) && c==delim){
				//separator char
				if(!g){
					tokens.add(res.toString().trim());
					res=new StringBuffer();
				}
				g=false;
			}else if(g){
				//delim necessary
			}else if( (!k) && i==(buf.length()-1)){
				//reach end
				tokens.add(res.toString().trim());
				res=new StringBuffer();
			}else{
				res.append(c);
			}
			
		}
		
		iter = tokens.iterator();
	}

	public boolean hasMoreElements()
	{
		return iter.hasNext();
	}

	public String nextElement()
	{
		return iter.next();
	}

	public void reset()
	{
		iter = tokens.iterator();
	}

}
