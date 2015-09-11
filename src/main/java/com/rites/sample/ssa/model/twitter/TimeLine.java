package com.rites.sample.ssa.model.twitter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.social.twitter.api.Tweet;

public class TimeLine implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Tweet> tweets;
	private String type;

	public TimeLine() {
		tweets = new ArrayList<>();
	}

	public TimeLine(List<Tweet> tweets, String type) {
		this.type = type;
		addTweets(tweets);
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public void addTweets(List<Tweet> tweets) {
		if (CollectionUtils.isNotEmpty(tweets)) {
			this.tweets.addAll(tweets);
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
