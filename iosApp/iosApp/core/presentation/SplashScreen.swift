//
//  SplashScreen.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 09-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct SplashScreen: View {
    var body: some View {
        
        Image("lt_logo")
            .resizable()
            .scaledToFit()
            .frame(width: 150, height: 150)
            .padding()
        
    }
}

#Preview {
    SplashScreen()
}
