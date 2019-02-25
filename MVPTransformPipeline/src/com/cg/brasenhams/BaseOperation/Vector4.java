package com.cg.brasenhams.BaseOperation;

public class Vector4 extends VectorBase {
    public Vector4()
    {
        elements = new float[4];
    }

    public Vector4(Vector3 from)
    {
        this();
        setX(from.getX());
        setY(from.getY());
        setZ(from.getZ());
        setW(1);
    }

    public Vector4(float x, float y, float z)
    {
        this();
        setX(x);
        setY(y);
        setZ(z);
        setW(1);
    }


    public Vector4(float x, float y, float z, float w)
    {
        this();
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
    }

    public Vector4(Vector4 from, Vector4 to)
    {
        this();
        setX(to.getX()-from.getX());
        setY(to.getY()-from.getY());
        setZ(to.getZ()-from.getZ());
        setW(1);
    }

    public Vector4 cross(Vector4 second)
    {
        float x = getY()*second.getZ() - getZ()*second.getY();
        float y = getZ()*second.getX() - second.getZ()*getX();
        float z =  getX()*second.getY() - getY()*second.getX();
        return new Vector4(x,y,z,1);
    }

    public float dot(Vector4 other)
    {
        float dotValue=0;
        for (int i=0;i<3;i++)
            dotValue+=(this.elements[i]*other.elements[i]);
        return dotValue;
    }


    public float getX()
    {
        return elements[0];
    }

    public float getY()
    {
        return elements[1];
    }

    public float getZ()
    {
        return elements[2];
    }
    public void setX(float value)
    {
        elements[0] = value;
    }

    public void setY(float value)
    {
        elements[1] = value;
    }

    public void setZ(float value)
    {
        elements[2] = value;
    }

    public void setW(float value)
    {
        elements[3] = value;
    }

    public float getW()
    {
        return elements[3];
    }

    public void copyValuesFrom(Vector4 other)
    {
        System.arraycopy(other.elements, 0, elements, 0, 4);
    }

    public Vector4 clone()
    {
        Vector4 clone = new Vector4();
        clone.copyValuesFrom(this);
        return clone;
    }

    public Vector4 unit()
    {
        float magnitude =0;
        for (int i = 0; i<3; i++)
            magnitude+=(elements[i]*elements[i]);

        Vector4 unit = new Vector4();
        unit.copyValuesFrom(this);
        if(magnitude!=0)
        {
            for (int i = 0; i<3; i++)
                unit.elements[i]/=magnitude;
        }
        return unit;
    }

    public Vector3 toVector3()
    {
        return new Vector3();
    }


    @Override
    public String toString() {
        return "X: "+getX()+" Y: "+getY()+" Z: "+getZ();
    }
}
