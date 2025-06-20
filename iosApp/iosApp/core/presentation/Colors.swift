//
//  Colors.swift
//  iosApp
//
//  Created by Leonardo Torrealba on 06-06-25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

extension Color {
    init(hex: Int64, alpha: Double = 1){
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 08) & 0xFF) / 255,
            blue: Double((hex >> 00) & 0xFF) / 255,
            opacity: alpha
        )
    }
    
    private static let colors = Colors()
    
    static let lightBlue = Color(hex: colors.LightBlue)
    static let lightBlueGrey = Color(hex: colors.LightBlueGrey)
    static let accentOrange = Color(hex: colors.AccentOrange)
    static let textBlack = Color(hex: colors.TextBlack)
    static let darkGray = Color(hex: colors.DarkGrey)
    
    static let primaryColor = Color(light: .accentOrange, dark: .accentOrange)
    static let background = Color(light: .lightBlueGrey, dark: .darkGray)
    static let onPrimary = Color(light: .white, dark: .white)
    static let onBackground = Color(light: .textBlack, dark: .white)
    static let surface = Color(light: .white, dark: .darkGray)
    static let onSurface = Color(light: .textBlack, dark: .white)
}

private extension Color {
    init(light: Self, dark: Self){
        self.init(uiColor: UIColor(light: UIColor(light), dark: UIColor(dark)))
    }
}

private extension UIColor {
    convenience init(light: UIColor, dark: UIColor){
        self.init { traits in
            switch traits.userInterfaceStyle {
            case .light, .unspecified:
                return light
            case .dark:
                return dark
            @unknown default:
                return light
            }
            
        }
    }
}
