package com.rites.sample.ssa.controller.twitter;

import java.io.Serializable;
import java.security.Principal;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Trends;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rites.sample.ssa.model.twitter.TimeLine;

@RestController
@RequestMapping("/api/connect/twitter")
public class TwitterController {
	
	// Yahoo Where On Earth ID representing the entire world
	private static final long WORLDWIDE_WOE = 1L;
	
	@Inject
	private ConnectionRepository connectionRepository;
	private final Twitter twitter;

	private TwitterProfile profile;
	
	@Inject
	public TwitterController(Twitter twitter) {
		this.twitter = twitter;
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public TwitterProfile fetchProfile(Principal currentUser, Model model) {
		Connection<Twitter> connection = connectionRepository.findPrimaryConnection(Twitter.class);
		if (connection == null) {
			return null;
		}
		if (profile == null) {
			profile = connection.getApi().userOperations().getUserProfile();
		}
		return profile;
	}
	
	@RequestMapping(value="/tweets", method=RequestMethod.GET)
	public TimeLine fetchTimeLine() {
		final TimeLine timeline = new TimeLine();
		TimelineOperations timelineOperations = twitter.timelineOperations();
//		
		timeline.addTweets(timelineOperations.getHomeTimeline());
		timeline.addTweets(timelineOperations.getUserTimeline());
		timeline.addTweets(timelineOperations.getMentions());
		timeline.addTweets(timelineOperations.getFavorites());
//		
		return timeline;
	}
	
	@RequestMapping(value="/tweet", method=RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void postTweet(@RequestBody TweetContent content) {
		if (content != null) {
			twitter.timelineOperations().updateStatus(content.getText());
		}
	}
	
	@RequestMapping(value="/friends", method=RequestMethod.GET)
	public CursoredList<TwitterProfile> friends(Model model) {
		return twitter.friendOperations().getFriends();
	}

	@RequestMapping(value="/followers", method=RequestMethod.GET)
	public CursoredList<TwitterProfile> followers(Model model) {
		return twitter.friendOperations().getFollowers();
	}
	
	@RequestMapping(value="/trends", method=RequestMethod.GET)
	public Trends showTrends(Model model) {
		return twitter.searchOperations().getLocalTrends(WORLDWIDE_WOE);
	}
	
	public static class TweetContent implements Serializable {
		private static final long serialVersionUID = 1L;
		private String text;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
}
