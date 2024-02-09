package edu.ucsd.cse110.successorator.util;

import androidx.lifecycle.MutableLiveData;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

//Code adapted from Lab 5
public class MutableLiveDataSubjectAdapter<T>
    extends LiveDataSubjectAdapter<T>
    implements MutableSubject<T>
    {
        private final MutableLiveData<T> mutableAdaptee;

    public MutableLiveDataSubjectAdapter(MutableLiveData<T> adaptee){
        super(adaptee);
        this.mutableAdaptee = adaptee;
    }

        @Override
        public void setValue(T value) {
        mutableAdaptee.setValue(value);
    }
}
