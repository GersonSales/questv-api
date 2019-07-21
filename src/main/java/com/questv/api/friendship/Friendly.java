package com.questv.api.friendship;

import com.questv.api.exception.FriendshipAcceptanceException;
import com.questv.api.exception.FriendshipRequestException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Friendly {

  void addFriend(final Friendly friend);

  void addRequest(final Friendly applicant);

  void removeRequest(final Friendly applicant);

  void acceptRequest(final Friendly applicant);

  List<Friendly> getAllFriends();

  void removeFriend(final Friendly friend);

  String getName();

  boolean hasFriendRequest();

  Integer getFriendsCount();


}

class User implements Friendly {
  private final Set<Friendly> friends;
  private final Set<Friendly> friendshipRequest;
  private final String name;

  private User(final String name) {
    this.name = name;
    this.friends = new HashSet<>();
    this.friendshipRequest = new HashSet<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFriend(final Friendly friend) {
    this.friends.add(friend);
    removeRequest(friend);
  }

  @Override
  public void addRequest(final Friendly applicant) {
    this.friendshipRequest.add(applicant);
  }

  @Override
  public void removeRequest(final Friendly applicant) {
    this.friendshipRequest.remove(applicant);
  }

  @Override
  public void acceptRequest(final Friendly applicant) {
    if (this.friendshipRequest.contains(applicant)) {
      this.addFriend(applicant);
      applicant.addFriend(this);
    } else {
      throw new FriendshipAcceptanceException();
    }
  }

  @Override
  public List<Friendly> getAllFriends() {
    return new ArrayList<>(this.friends);
  }

  @Override
  public void removeFriend(final Friendly friend) {
    if (this.friends.contains(friend)) {
      this.friends.remove(friend);
      friend.removeFriend(this);
    }
  }

  @Override
  public boolean hasFriendRequest() {
    return !this.friendshipRequest.isEmpty();
  }

  @Override
  public Integer getFriendsCount() {
    return this.friends.size();
  }

  public static void main(String[] args) {
    final User gerson = new User("Gerson");
    final User marcos = new User("Marcos");
    assert !gerson.hasFriendRequest();
    assert !marcos.hasFriendRequest();

    gerson.addRequest(marcos);
    assert gerson.hasFriendRequest();

    marcos.addRequest(gerson);
    assert marcos.hasFriendRequest();

    gerson.acceptRequest(marcos);
    assert !gerson.hasFriendRequest();
    assert !marcos.hasFriendRequest();

    assert gerson.getFriendsCount() == 1;
    assert marcos.getFriendsCount() == 1;

    gerson.removeFriend(marcos);
    assert gerson.getFriendsCount() == 0;
    assert marcos.getFriendsCount() == 0;

    try {
      gerson.acceptRequest(marcos);
    } catch (final FriendshipAcceptanceException error) {
      assert error.getMessage().equals("Friendship Acceptance Exception");
    }

    gerson.addRequest(marcos);


  }
}


