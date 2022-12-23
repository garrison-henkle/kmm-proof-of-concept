//
//  ContentView.swift
//  poc
//
//  Created by Garrison Henkle on 12/4/22.
//

import SwiftUI
import YouComponents

struct ContentView: View {
    @StateObject private var twitterRepo: TwitterRepo
    
    init(){
        CachedEndpointCompanion().initializeCacheDirectory(androidFilesDir: nil){ _, _ in }
        let api = TwitterApiImpl(apiToken: "abcdefghijklmnopqrstuvwxyz")
        let repo = TwitterRepository(api: api, filesDir: nil)
        _twitterRepo = StateObject(wrappedValue: TwitterRepo(repo: repo))
    }
    
    var body: some View {
        Text(twitterRepo.tweets.debugDescription)
    }
}

class TwitterRepo: ObservableObject{
    @Published var tweets: TweetList = TweetList(data: [])

    private var closable: Closable? = nil
    init(repo: TwitterRepository){
        repo.getTweets(query: nil, count: 20, safeFilter: true, shuffle: true, id: 0){ responseKt, error in
        let response = Response<TweetList>(responseKt!)
            switch(response){
            case .success(let resp):
                DispatchQueue.main.async {
                    self.tweets = resp.result
                }
            case .error(let error):
                print("Error occurred: \(error.ex)")
            }
        }
    }

    deinit{
        closable?.close()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

public enum Response<T : AnyObject> {

    case error(ResponseError<T>)
    case success(ResponseSuccess<T>)

    public var sealed: YouComponents.Response {
        switch self {
        case .error(let obj):
            return obj as YouComponents.Response
        case .success(let obj):
            return obj as YouComponents.Response
        }
    }

    public init(_ obj: YouComponents.Response) {
        if let obj = obj as? YouComponents.ResponseError<T> {
            self = .error(obj)
        } else if let obj = obj as? YouComponents.ResponseSuccess<T> {
            self = .success(obj)
        } else {
            fatalError("ResponseKs not synchronized with Response class")
        }
    }
}
