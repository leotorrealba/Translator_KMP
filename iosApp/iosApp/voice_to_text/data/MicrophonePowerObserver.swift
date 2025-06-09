//
//  MicrophonePowerObserver.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 09-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class MicrophonePowerObserver: ObservableObject {
    private var cancellable: AnyCancellable? = nil
    private var audioRecorder: AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    
    private let powerRatioEmissionsPerSecond = 20.0
    
    func startObserving() {
        do {
            let recordingSettings: [String: Any] = [
                AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1,
            ]
            
            let recorder = try AVAudioRecorder(url: URL(fileURLWithPath: "/dev/null", isDirectory: true), settings: recordingSettings)
            recorder.isMeteringEnabled = true
            recorder.record()
            self.audioRecorder = recorder
            
            self.cancellable = Timer.publish(
                every: 1.0 / powerRatioEmissionsPerSecond,
                tolerance: 1.0 / powerRatioEmissionsPerSecond,
                on: .main,
                in: .common
            )
                .autoconnect()
                .sink { [weak self] _ in
                    recorder.updateMeters()
                    
                    let powerOffset = recorder.averagePower(forChannel: 0)
                    if powerOffset < -50 {
                        self?.micPowerRatio = 0.0
                    } else {
                        let normalizedPowerOffset = CGFloat(50 + powerOffset) / 50
                        self?.micPowerRatio = normalizedPowerOffset
                    }
                }
        } catch {
            print("An error occurred when observing microphone power: \(error.localizedDescription)")
        }
    }
    
    func release() {
        cancellable = nil
        
        audioRecorder?.stop()
        audioRecorder = nil
        
        micPowerRatio = 0.0
    }
    
}
