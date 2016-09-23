package com.wongs.views.AirconditionerView;

/**
 * Created by sky on 2015/10/6.
 */
public interface LXModelListener
{

    public abstract void updateReceived(String lxroot, LXRequestConstants.JSON_NODE json_node);
}
