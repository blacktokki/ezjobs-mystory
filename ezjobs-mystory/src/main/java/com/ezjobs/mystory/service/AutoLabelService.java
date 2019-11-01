package com.ezjobs.mystory.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.StringArrayIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;

@Service
public class AutoLabelService {
	public void temp() {
		String[] array={"aaafsdfsf ds  fsd fdsa","bbbfadslf hsdkjlhfj ksadhkfhks ahfjhsadjhfb"};	
		temp(array);
	}
	
	private void temp(String[] array) {
		ArrayList<Pipe> pipelist=new ArrayList<Pipe>();
		pipelist.add(new CharSequence2TokenSequence());
		pipelist.add(new TokenSequence2FeatureSequence());
		InstanceList instances=new InstanceList(new SerialPipes(pipelist));
		instances.addThruPipe(new StringArrayIterator(array));
		ParallelTopicModel model=new ParallelTopicModel(3,1.0,0.01);
		model.addInstances(instances);
		model.setNumThreads(2);
		model.setNumIterations(100);
		try {
			model.estimate();
			ArrayList<TreeSet<IDSorter>> topicSortedWords=model.getSortedWords();
			for(TreeSet<IDSorter> tsw:topicSortedWords) {
				System.out.println("01:"+model.getAlphabet());
				System.out.println("02:"+tsw.iterator());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
