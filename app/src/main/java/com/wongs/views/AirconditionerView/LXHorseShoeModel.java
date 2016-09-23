package com.wongs.views.AirconditionerView;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class LXHorseShoeModel
{
    public static final int COOLING_MAX = 90;
    public static final int COOLING_MIN = 63;
    public static final int DEAD_BAND = 3;
    public static final int HEATING_MAX = 87;
    public static final int HEATING_MIN = 60;
    public static final int SINGLE_SET_POINT_MAX = 90;
    public static final int SINGLE_SET_POINT_MIN = 60;
    private int obCoolSP;
    private LXFanMode obFanMode;
    private int obHeatSP;
    private Set<LXOnHorseShoeChangeListener> obHorseShoeChangeListeners = new LinkedHashSet();
    private int obIndoorSP;
    private boolean obIsAway;
    private boolean obIsFeelsLike;
    private boolean obIsSingleSetPoint;
    private boolean obIsUsingSchedule;
    private int obLastPubCoolSP;
    private int obLastPubHeatSP;
    //private LXSchedule obSelectedSchedule;
    private int obSingleSetPointSP;
    private LXSystemMode obSystemMode;
    private UI_MODE obUIMode;
    boolean blnTempC=false;

    public void addHorseShoeListener(LXOnHorseShoeChangeListener paramLXOnHorseShoeChangeListener)
    {
        if (this.obHorseShoeChangeListeners != null)
            this.obHorseShoeChangeListeners.add(paramLXOnHorseShoeChangeListener);
    }

    public int getCoolSP()
    {
        return this.obCoolSP;
    }

    public LXFanMode getFanMode()
    {
        return this.obFanMode;
    }

    public int getHeatSP()
    {
        return this.obHeatSP;
    }

    public int getIndoorSP()
    {
        return this.obIndoorSP;
    }

    public boolean getIsAway()
    {
        return this.obIsAway;
    }

//    public LXSchedule getSelectedSchedule()
//    {
//        if (this.obSelectedSchedule == null)
//            this.obSelectedSchedule = LXScheduleManager.getInstance().getSaveEnergySchedule();
//        return this.obSelectedSchedule;
//    }

    public int getSingleSetPointSP()
    {
        return this.obSingleSetPointSP;
    }

    public LXSystemMode getSystemMode()
    {
        return this.obSystemMode;
    }

    public UI_MODE getUIMode()
    {
        return this.obUIMode;
    }

    public boolean isFeelsLike()
    {
        return this.obIsFeelsLike;
    }

    public boolean isSingleSetPoint()
    {
        return this.obIsSingleSetPoint;
    }

    public boolean isUsingSchedule()
    {
        return this.obIsUsingSchedule;
    }

    public void removeAllListeners()
    {
        if (this.obHorseShoeChangeListeners != null)
            this.obHorseShoeChangeListeners.clear();
    }

    public void removeHorseShoeListener(LXOnHorseShoeChangeListener paramLXOnHorseShoeChangeListener)
    {
        if (this.obHorseShoeChangeListeners != null)
            this.obHorseShoeChangeListeners.remove(paramLXOnHorseShoeChangeListener);
    }

    public void setAway(boolean paramBoolean)
    {
        this.obIsAway = paramBoolean;
    }



    public void setFanMode(LXFanMode paramLXFanMode, boolean paramBoolean)
    {
        this.obFanMode = paramLXFanMode;
        Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
        while (localIterator.hasNext())
            ((LXOnHorseShoeChangeListener)localIterator.next()).onFanModeChanged(paramLXFanMode, paramBoolean);
    }

    public void setFeelsLike(boolean paramBoolean)
    {
        this.obIsFeelsLike = paramBoolean;
        Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
        while (localIterator.hasNext())
            ((LXOnHorseShoeChangeListener)localIterator.next()).onUIModeChanged(UI_MODE.UI_VIEW_MODE, false);
    }
    public void setCoolSP(int paramInt, boolean flag)
    {
        int iMinSP=63;
        int iMaxSP=90;
        int iGap=3;
        if(blnTempC)
        {
            iMinSP=18;
            iMaxSP=32;
            iGap=2;
        }

        if((obCoolSP != paramInt || flag && paramInt != obLastPubCoolSP) && paramInt >= obHeatSP && paramInt >= iMinSP && paramInt <= iMaxSP)
        {
            if(paramInt - obHeatSP < iGap)
            {
                obHeatSP = paramInt - iGap;

                Iterator localIterator2 = this.obHorseShoeChangeListeners.iterator();
                while (localIterator2.hasNext())
                {
                    ((LXOnHorseShoeChangeListener) localIterator2.next()).onHeatSPChanged(this.obHeatSP, flag);
                }
                if(flag)
                    obLastPubHeatSP = obHeatSP;
            }
            obCoolSP = paramInt;
            Iterator localIterator1 = this.obHorseShoeChangeListeners.iterator();
            while (localIterator1.hasNext())
            {
                ((LXOnHorseShoeChangeListener) localIterator1.next()).onCoolSPChanged(paramInt, flag);

            }
            if(flag)
            {
                obLastPubCoolSP = paramInt;
                return;
            }
        }




        //        if (((this.obCoolSP == paramInt) && ((paramBoolean != true) || (paramInt == this.obLastPubCoolSP))) || (paramInt < this.obHeatSP));
        //        do
        //        {
        //            do
        //                return;
        //            while ((paramInt < 63) || (paramInt > 90));
        //            if (paramInt - this.obHeatSP < 3)
        //            {
        //                this.obHeatSP = (paramInt - 3);
        //                Iterator localIterator2 = this.obHorseShoeChangeListeners.iterator();
        //                while (localIterator2.hasNext())
        //                    ((LXOnHorseShoeChangeListener)localIterator2.next()).onHeatSPChanged(this.obHeatSP, paramBoolean);
        //                if (paramBoolean == true)
        //                    this.obLastPubHeatSP = this.obHeatSP;
        //            }
        //            this.obCoolSP = paramInt;
        //            Iterator localIterator1 = this.obHorseShoeChangeListeners.iterator();
        //            while (localIterator1.hasNext())
        //                ((LXOnHorseShoeChangeListener)localIterator1.next()).onCoolSPChanged(paramInt, paramBoolean);
        //        }
        //        while (paramBoolean != true);
        //        this.obLastPubCoolSP = paramInt;
    }

    public void setTempDisMode_Only(boolean blnModeC)
    {
        this.blnTempC = blnModeC;

    }

    public void setTempDisMode(boolean blnModeC)
    {
        this.blnTempC = blnModeC;
        if(blnTempC)
        {
            setHeatSP_Only(Math.round((getHeatSP() - 32.0f) / 1.8f));
            setCoolSP_Only(Math.round((getCoolSP() - 32.0f) / 1.8f));


        }
        else
        {
            setHeatSP_Only(Math.round(getHeatSP() * 1.8f + 32.0f));
            setCoolSP_Only(Math.round(getCoolSP() * 1.8f + 32.0f));

        }
        setIndoorSP(getIndoorSP() );
    }

    public void setHeatSP_Only(int paramInt)
    {
        this.obHeatSP = paramInt;
    }

    public void setCoolSP_Only(int paramInt)
    {
        this.obCoolSP = paramInt;

    }

    public void setIndoorSP_Only(int paramInt)
    {
        this.obIndoorSP = paramInt;

    }

    public void setHeatSP(int i, boolean flag)
    {

        int iMinSP=60;
        int iMaxSP=87;
        int iGap=3;
        if(blnTempC)
        {
            iMinSP=16;
            iMaxSP=30;
            iGap=2;
        }

        if((obHeatSP != i || flag && i != obLastPubHeatSP) && i <= obCoolSP && i <= iMaxSP && i >= iMinSP)
        {
            if(obCoolSP - i < iGap)
            {
                obCoolSP = i + iGap;
                for(Iterator iterator1 = obHorseShoeChangeListeners.iterator(); iterator1.hasNext();
                    ((LXOnHorseShoeChangeListener)iterator1.next()).onCoolSPChanged(obCoolSP, flag));
                if(flag)
                    obLastPubCoolSP = obCoolSP;
            }
            obHeatSP = i;
            for(Iterator iterator = obHorseShoeChangeListeners.iterator(); iterator.hasNext();
                ((LXOnHorseShoeChangeListener)iterator.next()).onHeatSPChanged(i, flag));
            if(flag)
            {
                obLastPubHeatSP = i;
                return;
            }
        }


//        if (((this.obHeatSP == paramInt) && ((paramBoolean != true) || (paramInt == this.obLastPubHeatSP))) || (paramInt > this.obCoolSP));
//        if(paramBoolean != true)
//        {
//            if((paramInt > 87) || (paramInt < 60))
//            {
//                return;
//            }
//
//
//            if (this.obCoolSP - paramInt < 3)
//            {
//                this.obCoolSP = (paramInt + 3);
//                Iterator localIterator2 = this.obHorseShoeChangeListeners.iterator();
//                while (localIterator2.hasNext())
//                    ((LXOnHorseShoeChangeListener)localIterator2.next()).onCoolSPChanged(this.obCoolSP, paramBoolean);
//                if (paramBoolean == true)
//                    this.obLastPubCoolSP = this.obCoolSP;
//            }
//            this.obHeatSP = paramInt;
//            Iterator localIterator1 = this.obHorseShoeChangeListeners.iterator();
//            while (localIterator1.hasNext())
//                ((LXOnHorseShoeChangeListener)localIterator1.next()).onHeatSPChanged(paramInt, paramBoolean);
//        }
//
//        this.obLastPubHeatSP = paramInt;
    }

    public void setIndoorSP(int paramInt)
    {

        this.obIndoorSP = paramInt;
        Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
        while (localIterator.hasNext())
            ((LXOnHorseShoeChangeListener)localIterator.next()).onIndoorSPChanged(paramInt);
    }

//    public void setIsUsingSchedule(boolean paramBoolean)
//    {
//        this.obIsUsingSchedule = paramBoolean;
//        if (this.obIsUsingSchedule)
//            switch (getSelectedSchedule().getType().ordinal())
//        {
//            default:
//            case 1:
//            case 2:
//            case 3:
//        }
//        while (true)
//        {
//            Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
//            while (localIterator.hasNext())
//                ((LXOnHorseShoeChangeListener)localIterator.next()).onIsUsingSchedulesChanged(this.obIsUsingSchedule);
//            setSystemMode(LXSystemMode.HEAT_AND_COOL, true);
//            continue;
//            setSystemMode(LXSystemMode.HEAT, true);
//            continue;
//            setSystemMode(LXSystemMode.COOL, true);
//        }
//    }

//    public void setSelectedSchedule(LXSchedule paramLXSchedule)
//    {
//        this.obSelectedSchedule = paramLXSchedule;
//        if (this.obIsUsingSchedule);
//        switch (1.$SwitchMap$com$lennox$ic3$model$LXSchedule$LXType[getSelectedSchedule().getType().ordinal()])
//        {
//            default:
//                return;
//            case 1:
//                setSystemMode(LXSystemMode.HEAT_AND_COOL, true);
//                return;
//            case 2:
//                setSystemMode(LXSystemMode.HEAT, true);
//                return;
//            case 3:
//        }
//        setSystemMode(LXSystemMode.COOL, true);
//    }

    public void setSingleSetPoint(boolean paramBoolean)
    {
        this.obIsSingleSetPoint = paramBoolean;
        Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
        while (localIterator.hasNext())
            ((LXOnHorseShoeChangeListener)localIterator.next()).onIsSingleSetPointChanged(this.obIsSingleSetPoint);
    }

    public void setSingleSetPointSP(int i, boolean flag)
    {

        if(obSingleSetPointSP != i && i >= 60 && i <= 90)
        {
            obSingleSetPointSP = i;
            Iterator iterator = obHorseShoeChangeListeners.iterator();
            while(iterator.hasNext())
                ((LXOnHorseShoeChangeListener)iterator.next()).onSingleSetPointSPChanged(i, flag);
        }
//        if ((this.obSingleSetPointSP == paramInt) || (paramInt < 60));
//        while (true)
//        {
//            return;
//            if (paramInt > 90)
//                continue;
//            this.obSingleSetPointSP = paramInt;
//            Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
//            while (localIterator.hasNext())
//                ((LXOnHorseShoeChangeListener)localIterator.next()).onSingleSetPointSPChanged(paramInt, paramBoolean);
//        }
    }

    public void setSystemMode(LXSystemMode paramLXSystemMode, boolean paramBoolean)
    {
        if (paramLXSystemMode == LXSystemMode.EMERGENCY_HEAT)
            paramLXSystemMode = LXSystemMode.HEAT;
        this.obSystemMode = paramLXSystemMode;
        Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
        while (localIterator.hasNext())
            ((LXOnHorseShoeChangeListener)localIterator.next()).onSystemModeChanged(paramLXSystemMode, paramBoolean);
    }

    public void setUIMode(UI_MODE paramUI_MODE, boolean paramBoolean)
    {


        if (this.obUIMode != paramUI_MODE)
        {
            if (paramUI_MODE == UI_MODE.UI_AWAY)
                setAway(true);

                this.obUIMode = paramUI_MODE;
                Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
                while (localIterator.hasNext())
                    ((LXOnHorseShoeChangeListener)localIterator.next()).onUIModeChanged(paramUI_MODE, paramBoolean);
                if (paramUI_MODE == UI_MODE.UI_VIEW_MODE)
                    setAway(false);

        }
//
//        if(obUIMode != paramUI_MODE)
//        {
//
//            if (paramUI_MODE != UI_MODE.UI_AWAY)
//            {
//                if (paramUI_MODE == UI_MODE.UI_VIEW_MODE)
//                    setAway(false);
//
//                obUIMode = paramUI_MODE;
//                Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
//                while (localIterator.hasNext())
//                    ((LXOnHorseShoeChangeListener)localIterator.next()).onUIModeChanged(paramUI_MODE, paramBoolean); /* Loop/switch isn't completed */
//            }
//            else
//            {
//                setAway(true);
//            }
//
//
//
//        }




//        if (this.obUIMode != paramUI_MODE)
//        {
//            if (paramUI_MODE == UI_MODE.UI_AWAY)
//                setAway(true);
//            while (true)
//            {
//                this.obUIMode = paramUI_MODE;
//                Iterator localIterator = this.obHorseShoeChangeListeners.iterator();
//                while (localIterator.hasNext())
//                    ((LXOnHorseShoeChangeListener)localIterator.next()).onUIModeChanged(paramUI_MODE, paramBoolean);
//                if (paramUI_MODE != UI_MODE.UI_VIEW_MODE)
//                    continue;
//                setAway(false);
//            }
//        }
    }

    public void updateLastPublish()
    {
        this.obLastPubHeatSP = this.obHeatSP;
        this.obLastPubCoolSP = this.obCoolSP;
    }

    public static abstract interface LXOnHorseShoeChangeListener
    {
        public abstract void onCoolSPChanged(int paramInt, boolean paramBoolean);

        public abstract void onFanModeChanged(LXFanMode paramLXFanMode, boolean paramBoolean);

        public abstract void onHeatSPChanged(int paramInt, boolean paramBoolean);

        public abstract void onIndoorSPChanged(int paramInt);

        public abstract void onIsSingleSetPointChanged(boolean paramBoolean);

        public abstract void onIsUsingSchedulesChanged(boolean paramBoolean);

        public abstract void onSingleSetPointSPChanged(int paramInt, boolean paramBoolean);

        public abstract void onSystemModeChanged(LXSystemMode paramLXSystemMode, boolean paramBoolean);

        public abstract void onUIModeChanged(UI_MODE paramUI_MODE, boolean paramBoolean);
    }

    public  enum UI_MODE
    {

            UI_VIEW_MODE,
            UI_HEAT_EDIT_MODE,
            UI_COOL_EDIT_MODE,
            UI_SINGLE_SET_POINT_EDIT_MODE,
            UI_SELECT,
            UI_AWAY,
            UI_DISCONNECTED,
            UI_CONNECTED,

    }
}