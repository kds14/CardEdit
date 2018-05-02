package app;

import java.util.ArrayList;

public class Card
{
	private String id;
	private String name;
	private String text;
	private int imageIndex = -1;
	private boolean collectable;
	
	/**
	 * Makes all the fields in this class into a format
	 * that can be displayed by a table in Swing.
	 * 
	 * @return data vector of the card's data
	 */
	public Object[][] getDataVector()
	{
		ArrayList<Object[]> r = new ArrayList<Object[]>();
		if (id != null && !id.isEmpty())
			r.add(new Object[] {"Id", id});
		if (name != null && !name.isEmpty())
			r.add(new Object[] {"Name", name});
		if (text != null && !text.isEmpty())
			r.add(new Object[] {"Text", text});
		r.add(new Object[] {"Collectable", collectable});
		if (imageIndex >= 0)
			r.add(new Object[] {"ImageIndex", imageIndex});
		Object[][] o = new Object[r.size()][];
		for(int i = 0; i < r.size(); i++)
		{
			o[i] = r.get(i);
		}
		return o;
	}
	
	public int getImageIndex()
	{
		return imageIndex;
	}
	
	public void setImageIndex(String imageIndex)
	{
		
		this.imageIndex = Integer.parseInt(imageIndex);
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public boolean getCollectable()
	{
		return collectable;
	}
	
	public void setCollectable(boolean collectable)
	{
		this.collectable = collectable;
	}
	
	@Override
	public String toString()
	{
		String ii = "";
		if (imageIndex != -1)
		{
			ii = ", imageIndex: " + imageIndex + ", ";
		}
				
		return "[id: " + id + ", name: " + name + ", text: " + text + ", " + 
				ii + "collectable: " + collectable + "]";
	}
	
}